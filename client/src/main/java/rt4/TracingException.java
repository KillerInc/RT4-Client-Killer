package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

import java.io.*;
import java.net.URL;

@OriginalClass("client!ld")
public final class TracingException extends RuntimeException {

	@OriginalMember(owner = "client!ld", name = "e", descriptor = "Ljava/lang/String;")
	public String message;

	@OriginalMember(owner = "client!ld", name = "f", descriptor = "Ljava/lang/Throwable;")
	public Throwable cause;

	@OriginalMember(owner = "client!ha", name = "a", descriptor = "(Ljava/lang/String;Ljava/lang/Throwable;B)V")
	public static void report(@OriginalArg(0) String message, @OriginalArg(1) Throwable throwable) {
		try {
			@Pc(13) String errorStr = "";
			if (throwable != null) {
				errorStr = getStackTraceString(throwable);
			}
			if (message != null) {
				if (throwable != null) {
					errorStr = errorStr + " | ";
				}
				errorStr = errorStr + message;
			}
			printError(errorStr);
			errorStr = replaceAll(":", "%3a", errorStr);
			errorStr = replaceAll("@", "%40", errorStr);
			errorStr = replaceAll("&", "%26", errorStr);
			errorStr = replaceAll("#", "%23", errorStr);
			if (GameShell.signLink2.applet == null) {
				return;
			}
			@Pc(109) PrivilegedRequest request = GameShell.signLink2.openUrlStream(new URL(GameShell.signLink2.applet.getCodeBase(), "clienterror.ws?c=" + GameShell.clientBuild + "&u=" + Player.name37 + "&v1=" + SignLink.javaVendor + "&v2=" + SignLink.javaVersion + "&e=" + errorStr));
			while (request.status == 0) {
				ThreadUtils.sleep(1L);
			}
			if (request.status == 1) {
				@Pc(128) DataInputStream stream = (DataInputStream) request.result;
				stream.read();
				stream.close();
			}
		} catch (@Pc(135) Exception ex) {
		}
	}

	@OriginalMember(owner = "client!af", name = "a", descriptor = "(ILjava/lang/String;)V")
	public static void printError(@OriginalArg(1) String error) {
		System.out.println("Error: " + replaceAll("%0a", "\n", error));
	}

	@OriginalMember(owner = "client!hi", name = "a", descriptor = "(ILjava/lang/Throwable;)Ljava/lang/String;")
	public static String getStackTraceString(@OriginalArg(1) Throwable throwable) throws IOException {
		@Pc(24) String result;
		if (throwable instanceof TracingException) {
			@Pc(11) TracingException tracing = (TracingException) throwable;
			throwable = tracing.cause;
			result = tracing.message + " | ";
		} else {
			result = "";
		}
		@Pc(32) StringWriter sw = new StringWriter();
		@Pc(37) PrintWriter pw = new PrintWriter(sw);
		throwable.printStackTrace(pw);
		pw.close();
		@Pc(45) String trace = sw.toString();
		@Pc(53) BufferedReader reader = new BufferedReader(new StringReader(trace));
		@Pc(56) String firstLine = reader.readLine();
		while (true) {
			@Pc(59) String line = reader.readLine();
			if (line == null) {
				return result + "| " + firstLine;
			}
			@Pc(65) int openParen = line.indexOf(40);
			@Pc(72) int closeParen = line.indexOf(41, openParen + 1);
			@Pc(79) String method;
			if (openParen == -1) {
				method = line;
			} else {
				method = line.substring(0, openParen);
			}
			method = method.trim();
			method = method.substring(method.lastIndexOf(32) + 1);
			method = method.substring(method.lastIndexOf(9) + 1);
			result = result + method;
			if (openParen != -1 && closeParen != -1) {
				@Pc(126) int javaIdx = line.indexOf(".java:", openParen);
				if (javaIdx >= 0) {
					result = result + line.substring(javaIdx + 5, closeParen);
				}
			}
			result = result + ' ';
		}
	}

	@OriginalMember(owner = "client!da", name = "a", descriptor = "(Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;)Ljava/lang/String;")
	public static String replaceAll(@OriginalArg(0) String target, @OriginalArg(1) String replacement, @OriginalArg(3) String str) {
		for (@Pc(5) int idx = str.indexOf(target); idx != -1; idx = str.indexOf(target, idx + replacement.length())) {
			str = str.substring(0, idx) + replacement + str.substring(target.length() + idx);
		}
		return str;
	}
}
