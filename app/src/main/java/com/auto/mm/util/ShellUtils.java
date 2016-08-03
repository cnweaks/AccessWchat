package com.auto.mm.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * ShellUtils

 */
public class ShellUtils {

    public static final String COMMAND_SU       = "su";
    public static final String COMMAND_SH       = "sh";
    public static final String COMMAND_EXIT     = "exit\n";
    public static final String COMMAND_LINE_END = "\n";

    private ShellUtils() {
        throw new AssertionError();
    }

    /**
     * 验证root超级权限
     *
     * @return
     */
    public static boolean checkRootPermission() {
        return execCommand("echo root", true, false).result == 0;
    }

    /**
     * 执行 shell 命令，默认返回结果味精
     *
     * @param command 命令
     * @param isRoot 是否需要运行与根
     * @return
     * @see ShellUtils#execCommand(String[], boolean, boolean)
     */
    public static CommandResult execCommand(String command, boolean isRoot) {
        return execCommand(new String[] {command}, isRoot, true);
    }

    /**
     * 执行 shell 命令，默认返回结果味精
     *
     * @param commands 命令列表
     * @param isRoot 是否需要运行与根
     * @return
     * @see ShellUtils#execCommand(String[], boolean, boolean)
     */
    public static CommandResult execCommand(List<String> commands, boolean isRoot) {
        return execCommand(commands == null ? null : commands.toArray(new String[] {}), isRoot, true);
    }

    /**
     * 执行 shell 命令，默认返回结果味精
     *
     * @param commands 命令数组
     * @param isRoot 是否需要运行与根
     * @return
     * @see ShellUtils#execCommand(String[], boolean, boolean)
     */
    public static CommandResult execCommand(String[] commands, boolean isRoot) {
        return execCommand(commands, isRoot, true);
    }

    /**
     * 执行 shell 命令
     *
     * @param command 命令
     * @param isRoot 是否需要运行与根
     * @param isNeedResultMsg 是否需要结果消息
     * @return
     * @see ShellUtils#execCommand(String[], boolean, boolean)
     */
    public static CommandResult execCommand(String command, boolean isRoot, boolean isNeedResultMsg) {
        return execCommand(new String[] {command}, isRoot, isNeedResultMsg);
    }

    /**
     * 执行 shell 命令
     *
     * @param commands 命令列表
     * @param isRoot 是否需要运行与根
     * @param isNeedResultMsg 是否需要导致味精
     * @return
     * @see ShellUtils#execCommand(String[], boolean, boolean)
     */
    public static CommandResult execCommand(List<String> commands, boolean isRoot, boolean isNeedResultMsg) {
        return execCommand(commands == null ? null : commands.toArray(new String[] {}), isRoot, isNeedResultMsg);
    }

    /**
     * 执行 shell 命令
     *
     * @param commands 命令数组
     * @param isRoot 是否需要运行与根
     * @param isNeedResultMsg 是否需要结果
     * @return <ul>
     *         <li>如果 isNeedResultMsg 是假的, {@link CommandResult#successMsg} 为 null 和
     *         {@link CommandResult#errorMsg} is null.</li>
     *         <li>if {@link CommandResult#result} 是-1，有可能一些 excepiton.</li>
     *         </ul>
     */
    public static CommandResult execCommand(String[] commands, boolean isRoot, boolean isNeedResultMsg) {
        int result = -1;
        if (commands == null || commands.length == 0) {
            return new CommandResult(result, null, null);
        }

        Process process = null;
        BufferedReader successResult = null;
        BufferedReader errorResult = null;
        StringBuilder successMsg = null;
        StringBuilder errorMsg = null;

        DataOutputStream os = null;
        try {
            process = Runtime.getRuntime().exec(isRoot ? COMMAND_SU : COMMAND_SH);
            os = new DataOutputStream(process.getOutputStream());
            for (String command : commands) {
                if (command == null) {
                    continue;
                }

                // 不要使用 os.writeBytes(commmand)，避免中国字符集错误
                os.write(command.getBytes());
                os.writeBytes(COMMAND_LINE_END);
                os.flush();
            }
            os.writeBytes(COMMAND_EXIT);
            os.flush();

            result = process.waitFor();
            // 得到命令的结果
            if (isNeedResultMsg) {
                successMsg = new StringBuilder();
                errorMsg = new StringBuilder();
                successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
                errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String s;
                while ((s = successResult.readLine()) != null) {
                    successMsg.append(s);
                }
                while ((s = errorResult.readLine()) != null) {
                    errorMsg.append(s);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (successResult != null) {
                    successResult.close();
                }
                if (errorResult != null) {
                    errorResult.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (process != null) {
                process.destroy();
            }
        }
        return new CommandResult(result, successMsg == null ? null : successMsg.toString(), errorMsg == null ? null
                : errorMsg.toString());
    }

    /**
     * 命令的结果
     * <ul>
     * <li>{@link CommandResult#result} 命令，0 表示正常的结果，否则意味着错误，相同的 linux shell 的品保制度</li>
     * <li>{@link CommandResult#successMsg} 命令结果手段成功消息</li>
     * <li>{@link CommandResult#errorMsg} 命令结果手段错误消息</li>
     * </ul>
     *
     * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2013-5-16
     */
    public static class CommandResult {

        /** 命令的结果 **/
        public int    result;
        /** 命令结果的成功消息 **/
        public String successMsg;
        /** 错误消息的命令结果 **/
        public String errorMsg;

        public CommandResult(int result) {
            this.result = result;
        }

        public CommandResult(int result, String successMsg, String errorMsg) {
            this.result = result;
            this.successMsg = successMsg;
            this.errorMsg = errorMsg;
        }
    }
}
