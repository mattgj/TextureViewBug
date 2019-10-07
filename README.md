# Android TextureView Bug

This is an example Android application which demonstrates what seems to be a bug with hardware accelerated TextureViews on Android 10. When the application runs, the counter on screen should continuously count up. However, when the bug is present, the view breaks after a few renders. Changing the sleep call in the render thread to a higher value, such as 100, will fix the issue.

