package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;

import java.applet.Applet;

public final class BrowserControl {

	@OriginalMember(owner = "client!gh", name = "a", descriptor = "(Ljava/lang/String;BLjava/applet/Applet;)Ljava/lang/Object;")
	public static Object call(@OriginalArg(0) String method, @OriginalArg(2) Applet applet) throws Throwable {
		return null; // JSObject.getWindow(applet).call(method, (Object[]) null);
	}

	@OriginalMember(owner = "client!gh", name = "a", descriptor = "(Ljava/applet/Applet;Ljava/lang/String;[Ljava/lang/Object;B)Ljava/lang/Object;")
	public static Object call(@OriginalArg(0) Applet applet, @OriginalArg(1) String method, @OriginalArg(2) Object[] args) throws Throwable {
		return null; // JSObject.getWindow(applet).call(method, args);
	}

	@OriginalMember(owner = "client!gh", name = "a", descriptor = "(Ljava/applet/Applet;ZLjava/lang/String;)V")
	public static void eval(@OriginalArg(0) Applet applet, @OriginalArg(2) String script) throws Throwable {
		// JSObject.getWindow(applet).eval(script);
	}
}
