package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

import javax.swing.*;
import java.awt.Component;
import java.awt.event.*;

@OriginalClass("client!ug")
public final class Mouse implements MouseListener, MouseMotionListener, FocusListener {

	@OriginalMember(owner = "client!ah", name = "s", descriptor = "I")
	public static int clickX = 0;
	@OriginalMember(owner = "client!em", name = "y", descriptor = "I")
	public static int clickY = 0;
	@OriginalMember(owner = "client!sc", name = "v", descriptor = "I")
	public static int lastMouseY = 0;
	@OriginalMember(owner = "client!rh", name = "o", descriptor = "I")
	public static int lastMouseX = 0;
	@OriginalMember(owner = "client!he", name = "bb", descriptor = "Lclient!ug;")
	public static Mouse instance = new Mouse();
	@OriginalMember(owner = "client!he", name = "Y", descriptor = "I")
	public static volatile int idleLoops = 0;
	@OriginalMember(owner = "client!lk", name = "Z", descriptor = "I")
	public static int clickButton = 0;
	@OriginalMember(owner = "client!bl", name = "Q", descriptor = "I")
	public static int pressedButton = 0;
	@OriginalMember(owner = "client!ra", name = "jb", descriptor = "J")
	public static volatile long pendingClickTime = 0L;
	@OriginalMember(owner = "client!ck", name = "k", descriptor = "I")
	public static volatile int pendingClickX = 0;
	@OriginalMember(owner = "client!eg", name = "w", descriptor = "I")
	public static volatile int pendingPressedButton = 0;
	@OriginalMember(owner = "client!kf", name = "c", descriptor = "J")
	public static long clickTime = 0L;
	@OriginalMember(owner = "client!dc", name = "W", descriptor = "I")
	public static volatile int pendingClickButton = 0;
	@OriginalMember(owner = "client!nb", name = "j", descriptor = "I")
	public static volatile int currentMouseY = -1;
	@OriginalMember(owner = "client!lh", name = "u", descriptor = "I")
	public static volatile int currentMouseX = -1;
	@OriginalMember(owner = "client!sa", name = "Y", descriptor = "I")
	public static volatile int pendingClickY = 0;
	@OriginalMember(owner = "client!wi", name = "W", descriptor = "I")
	public static int lastHandledClickX = 0;
	@OriginalMember(owner = "client!ok", name = "f", descriptor = "J")
	public static long prevClickTime = 0L;
	@OriginalMember(owner = "client!wl", name = "u", descriptor = "I")
	public static int lastHandledClickY = 0;

	@OriginalMember(owner = "client!sc", name = "a", descriptor = "(ILjava/awt/Component;)V")
	public static void stop(@OriginalArg(1) Component component) {
		component.removeMouseListener(instance);
		component.removeMouseMotionListener(instance);
		component.removeFocusListener(instance);
		pendingPressedButton = 0;
	}

	@OriginalMember(owner = "client!ug", name = "a", descriptor = "(I)V")
	public static void quit() {
		if (instance != null) {
			@Pc(5) Mouse mutex = instance;
			synchronized (instance) {
				instance = null;
			}
		}
	}

	@OriginalMember(owner = "client!ii", name = "b", descriptor = "(I)V")
	public static void loop() {
		@Pc(2) Mouse mutex = instance;
		synchronized (instance) {
			pressedButton = pendingPressedButton;
			lastMouseX = currentMouseX;
			lastMouseY = currentMouseY;
			clickButton = pendingClickButton;
			clickX = pendingClickX;
			idleLoops++;
			clickY = pendingClickY;
			clickTime = pendingClickTime;
			pendingClickButton = 0;
		}
	}

	@OriginalMember(owner = "client!h", name = "a", descriptor = "(Ljava/awt/Component;Z)V")
	public static void start(@OriginalArg(0) Component component) {
		component.addMouseListener(instance);
		component.addMouseMotionListener(instance);
		component.addFocusListener(instance);
	}

	@OriginalMember(owner = "client!lc", name = "a", descriptor = "(B)I")
	public static int getIdleLoops() {
		return idleLoops;
	}

	@OriginalMember(owner = "client!dl", name = "a", descriptor = "(II)V")
	public static void setIdleLoops(@OriginalArg(1) int value) {
		@Pc(10) Mouse mutex = instance;
		synchronized (instance) {
			idleLoops = value;
		}
	}

	@OriginalMember(owner = "client!ug", name = "mouseMoved", descriptor = "(Ljava/awt/event/MouseEvent;)V")
	@Override
	public final synchronized void mouseMoved(@OriginalArg(0) MouseEvent event) {
		if (instance != null) {
			idleLoops = 0;
			currentMouseX = event.getX();
			currentMouseY = event.getY();
		}
	}

	@OriginalMember(owner = "client!ug", name = "focusLost", descriptor = "(Ljava/awt/event/FocusEvent;)V")
	@Override
	public final synchronized void focusLost(@OriginalArg(0) FocusEvent event) {
		if (instance != null) {
			pendingPressedButton = 0;
		}
	}

	@OriginalMember(owner = "client!ug", name = "mouseDragged", descriptor = "(Ljava/awt/event/MouseEvent;)V")
	@Override
	public final synchronized void mouseDragged(@OriginalArg(0) MouseEvent event) {
		int x = event.getX();
		int y = event.getY();

		if (SwingUtilities.isMiddleMouseButton(event)) return;

		if (instance != null) {
			idleLoops = 0;
			currentMouseX = x;
			currentMouseY = y;
		}
	}

	@OriginalMember(owner = "client!ug", name = "mouseReleased", descriptor = "(Ljava/awt/event/MouseEvent;)V")
	@Override
	public final synchronized void mouseReleased(@OriginalArg(0) MouseEvent event) {
		if (instance != null) {
			idleLoops = 0;
			pendingPressedButton = 0;
			@Pc(14) int modifiers = event.getModifiers();
			if ((modifiers & 0x10) == 0) {
			}
			if ((modifiers & 0x4) == 0) {
			}
			if ((modifiers & 0x8) == 0) {
			}
		}
		if (event.isPopupTrigger()) {
			event.consume();
		}
	}

	@OriginalMember(owner = "client!ug", name = "mouseClicked", descriptor = "(Ljava/awt/event/MouseEvent;)V")
	@Override
	public final void mouseClicked(@OriginalArg(0) MouseEvent event) {
		if (event.isPopupTrigger()) {
			event.consume();
		}
	}

	@OriginalMember(owner = "client!ug", name = "focusGained", descriptor = "(Ljava/awt/event/FocusEvent;)V")
	@Override
	public final void focusGained(@OriginalArg(0) FocusEvent event) {
	}

	@OriginalMember(owner = "client!ug", name = "mousePressed", descriptor = "(Ljava/awt/event/MouseEvent;)V")
	@Override
	public final synchronized void mousePressed(@OriginalArg(0) MouseEvent event) {
		if (SwingUtilities.isMiddleMouseButton(event)) {
			return;
		}

		if (instance != null) {
			idleLoops = 0;
			pendingClickX = event.getX();
			pendingClickY = event.getY();
			pendingClickTime = MonotonicClock.currentTimeMillis();
			if ((event.getModifiersEx() & MouseEvent.BUTTON3_DOWN_MASK) == 0) {
				pendingClickButton = 1;
				pendingPressedButton = 1;
			} else {
				pendingClickButton = 2;
				pendingPressedButton = 2;
			}
			@Pc(29) int modifiers = event.getModifiers();
			if ((modifiers & 0x10) == 0) {
			}
			if ((modifiers & 0x4) != 0) {
			}
			if ((modifiers & 0x8) != 0) {
			}
		}
		if (event.isPopupTrigger()) {
			event.consume();
		}
	}

	@OriginalMember(owner = "client!ug", name = "mouseExited", descriptor = "(Ljava/awt/event/MouseEvent;)V")
	@Override
	public final synchronized void mouseExited(@OriginalArg(0) MouseEvent event) {
		if (instance != null) {
			idleLoops = 0;
			currentMouseX = -1;
			currentMouseY = -1;
		}
	}

	@OriginalMember(owner = "client!ug", name = "mouseEntered", descriptor = "(Ljava/awt/event/MouseEvent;)V")
	@Override
	public final synchronized void mouseEntered(@OriginalArg(0) MouseEvent event) {
		if (instance != null) {
			idleLoops = 0;
			currentMouseX = event.getX();
			currentMouseY = event.getY();
		}
	}
}
