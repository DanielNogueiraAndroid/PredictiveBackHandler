This is a repository with the intention to create a sample for this issue:\
https://issuetracker.google.com/issues/365027664

Steps to reproduce issue:

1 - Open the app\
2 - Navigate by clicking the navigate button until you get to item 3\
3 - Press the hardware back button 3 times very quickly

Expected:\
The app should show the text "Compose 1!"

Actual:\
The app will crash with an IndexOutOfBoundsException:

java.lang.IndexOutOfBoundsException: Index -1 out of bounds for length 1\
                                                                                                        at jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:64)\
                                                                                                        at jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:70)\
                                                                                                        at jdk.internal.util.Preconditions.checkIndex(Preconditions.java:266)\
                                                                                                        at java.util.Objects.checkIndex(Objects.java:359)\
                                                                                                        at java.util.ArrayList.get(ArrayList.java:434)\
                                                                                                        at androidx.navigation.compose.NavHostKt$NavHost$25$1.invokeSuspend(NavHost.kt:518)

Note:\
The current value of 1000 is arbitrary and just simulates some work being done: 
Thread.sleep(1000) // Simulate work during back handler

This value can be changed, for example to 200 milliseconds and the app will still crash if the user navigates quickly.
