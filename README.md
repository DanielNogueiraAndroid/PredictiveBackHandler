This is a repository with intention to create a sample for this issue\
https://issuetracker.google.com/issues/365027664

Steps to reproduce issue:

1 - Open the app\
2 - Navigate by clicking the only button until you get to item 3\
3 - Press hardware back button 3 times in rapid way

Expected:\
The app show the home (item 1)

Actual:\
The app will crash

java.lang.IndexOutOfBoundsException: Index -1 out of bounds for length 1\
                                                                                                        at jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:64)\
                                                                                                        at jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:70)\
                                                                                                        at jdk.internal.util.Preconditions.checkIndex(Preconditions.java:266)\
                                                                                                        at java.util.Objects.checkIndex(Objects.java:359)\
                                                                                                        at java.util.ArrayList.get(ArrayList.java:434)\
                                                                                                        at androidx.navigation.compose.NavHostKt$NavHost$25$1.invokeSuspend(NavHost.kt:518)

Note:\
This value can be changed, as example from 1000 to 200 milliseconds and the crash still happens if the use navigate quickly. 
Thread.sleep(1000) // simulate work during back handler
