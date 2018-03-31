





```
Start Failed: Failed to initialize graphics environment

java.awt.AWTError: Can't connect to X11 window server using '10.0' as the value of the DISPLAY variable.
        at sun.awt.X11GraphicsEnvironment.initDisplay(Native Method)
        at sun.awt.X11GraphicsEnvironment.access$200(X11GraphicsEnvironment.java:65)
        at sun.awt.X11GraphicsEnvironment$1.run(X11GraphicsEnvironment.java:115)
        at java.security.AccessController.doPrivileged(Native Method)
        at sun.awt.X11GraphicsEnvironment.<clinit>(X11GraphicsEnvironment.java:74)
        at java.lang.Class.forName0(Native Method)
        at java.lang.Class.forName(Class.java:264)
        at java.awt.GraphicsEnvironment.createGE(GraphicsEnvironment.java:103)
        at java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment(GraphicsEnvironment.java:82)
        at sun.awt.X11.XToolkit.<clinit>(XToolkit.java:128)
        at java.lang.Class.forName0(Native Method)
        at java.lang.Class.forName(Class.java:264)
        at java.awt.Toolkit$2.run(Toolkit.java:860)
        at java.awt.Toolkit$2.run(Toolkit.java:855)
        at java.security.AccessController.doPrivileged(Native Method)
        at java.awt.Toolkit.getDefaultToolkit(Toolkit.java:854)
        at java.awt.Toolkit.getEventQueue(Toolkit.java:1734)
        at java.awt.EventQueue.isDispatchThread(EventQueue.java:1049)
        at javax.swing.SwingUtilities.isEventDispatchThread(SwingUtilities.java:1361)
        at javax.swing.text.StyleContext.reclaim(StyleContext.java:454)
        at javax.swing.text.StyleContext.addAttribute(StyleContext.java:311)
        at javax.swing.text.html.StyleSheet.addAttribute(StyleSheet.java:578)
        at javax.swing.text.StyleContext$NamedStyle.addAttribute(StyleContext.java:1501)
        at javax.swing.text.StyleContext$NamedStyle.setName(StyleContext.java:1312)
        at javax.swing.text.StyleContext$NamedStyle.<init>(StyleContext.java:1259)
        at javax.swing.text.StyleContext.addStyle(StyleContext.java:107)
        at javax.swing.text.StyleContext.<init>(StyleContext.java:87)
        at javax.swing.text.html.StyleSheet.<init>(StyleSheet.java:166)
        at javax.swing.text.html.HTMLEditorKit.getStyleSheet(HTMLEditorKit.java:391)
        at com.intellij.util.ui.UIUtil.<clinit>(UIUtil.java:105)
        at com.intellij.ide.plugins.PluginManager.start(PluginManager.java:73)
        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.lang.reflect.Method.invoke(Method.java:498)
        at com.intellij.ide.Bootstrap.main(Bootstrap.java:39)
        at com.intellij.idea.Main.main(Main.java:83)
127.0.0.1 localhost 没有添加
```
