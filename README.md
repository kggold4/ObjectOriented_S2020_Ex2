# Object-Oriented Programming Course Assignment #3

### Authors: Kfir Goldfarb and Nadav Keysar

This assignment have two parts:

## Part 1:

Build directed weighted graph data structure, and graph algorithms class for the directed and wighted graphs.

## Part 2:

By part 1 graph data structure and graph algorithms build a client class that know how to play the best in pokemon game on the directed weighted graph (the game is work on a server called Ex2_Server_v0.13.jar - can see in libs folder), every interaction with the server is doing by json formats, the server can get a game level [from 0 to 23] and an ID of the student, and can build random directed weighted graph with some pokemon on his edges, the Ex2 class will do some inserting manipulations on the given graph and with the DWGraph_Algo class will use some algorithms in it aka shortestPathDist, shortestPath, isConnected and more (details below).
###### to read more details on the interfaces, classes, implementation, algorithms and the pokemon game, go to the wiki of this project - https://github.com/kggold4/ObjectOriented_S2020_Ex2/wiki

## packages and classes

פרויקט זה מייצג משחק שמבוסס על יישום גרף משוקלל דו כיווני עם שיטות ואלגוריתמים שונים.
במשחק זה עלינו לנהל קבוצה של סוכנים שהמטרה שלהם היא לאסוף כמה שיותר פוקמונים דרך אחד או יותר מכדורי הפוקמונים שלך לפני שייגמר הזמן. ככל שנתפוס יותר פוקמונים כך תצברו יותר נקודות. המשחק מתנהל על לוח משחק משתנה. למשחק יש מעט תרחישים שונים, המבוססים על גרפים שונים עם מגבלות זמן שונות, ברמות שונות. הפוקמונים נשארים סטטיים במקומותיהם ובעלי ערכים שונים. פוקמון אחד יכול להיות יקר מהשני. הסוכנים יכולים להגביר את המהירות אם תופסים מספיק פוקמונים.
המשחק מתנהל בארבעה שלבים:
בחירת שלב
מיקום הסוכנים למקום המוצא שלהם.
התחלת המשחק
ניהול מתמשך של הסוכנים עד שהמשחק יסתיים.

יישום הפרויקט:
גרף די אס-  מחלקב זו מיישמת גרף משוקלל מתמטי
