# To-do List - User Guide

By : `Team SE-EDU`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Jun 2016`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

1. [Quick Start](#1-quick-start)
2. [Features](#2-features)
3. [FAQ](#3-faq)
4. [Command Summary](#4-command-summary)

## 1. Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>

   > Having any Java 8 version is not enough. <br>
   > This app will not work with earlier versions of Java 8.

1. Download the latest `todolist.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your ToDoList.
3. Double-click the file to start the app. The GUI should appear in a few seconds.
   > <img src="images/Ui.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`list`** : lists all tasks
   * **`add`**`Do tutorial /by 14.02.17` :
     adds a deadline task describing `Do tutorial` to the todolist.
   * **`delete`**` f3` : deletes the 3rd task shown in the float list
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>


## 2. Features

> **Command Format**
>
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * Parameters can be in any order.

### 2.1. Viewing help : `help`

Format: `help [COMMAND]`

> * A list of all available commands will be shown in the display area if command parameter is absent.
> * The usage of the specified command will be shown in the display area if command parameter is present.

Examples:

* `help add`<br>
  Add command usage would be shown in the display area.

### 2.2. Adding a task: `add`

Adds a task to the todolist
Format: `add TITLE /venue [VENUE] /on [DATE] /from [DATE_TIME] /to [DATE_TIME] /by [DATE_TIME] /level [DATE_TIME] /description [DESCRIPTION] #[TAG]...`

> * TITLE represent the task (complusory) <br>
> * /venue represents the location for the task <br>
> * /on represents the date of the event task <br>
> * /from represents the starting time of the event task <br>
> Note: If /to parameter is not given as well, the task would be changed to a Todo task instead
> * /to represents the ending time of the event task <br>
> Note: If /from parameter is not given as well, the task would be changed to a deadline task instead
> * /by represents the deadline of the deadline task <br>
> Note: /to parameter and /by parameter are mutually exclusive
> * /level represents the urgency level of the task <br>
> Note: Tasks can have 4 level of priority (0 - 3) with 3 being the highest
> * /description represents the description of the task <br>
> * #[TAG...] represents the tag <br>
> Note: Tasks can have any number of tags (including 0)

### 2.3.1. Listing all tasks : `list`

Shows a list of tasks in the todolist<br>
Format: `list /from [DATE_TIME] /to [DATE_TIME]`

> * /from would show a list of tasks that have Start Time at the specified DATE_TIME (interchangeable with /on)
> * /to would show a list of tasks that have End Time at the specified DATE_TIME (interchangeable with /by)

Examples:

* `list`<br>
All tasks will be shown
* `list /from 7 April`<br>
Tasks with start time of 7 April will be shown

### 2.4. Editing a task : `edit`

Edits an existing task in the todolist.<br>
Format: `edit INDEX /title [TITLE] /venue [VENUE] /on [DATE] /from [DATE_TIME] /to [DATE_TIME] /by [DATE_TIME] /level [LEVEL] /description [DESCRIPTION] #[TAG]...`

> * Edits the task at the specified `INDEX`.
    The index refers to the index number shown in the last task listing.
    Index **must include the type of task**, e.g. f1 for 1st float task and **must be followed by a positive integer** 1, 2, 3, ... <br>
> * At least one of the optional fields must be provided.
> * Existing values will be updated to the input values.
> * When editing tags, the existing tags of the task will be removed i.e adding of tags is not cumulative.

Examples:

* `edit d1 /title Do tutorial /to 17.09.19`<br>
  Edits the title and deadline of the 1st float task to be `Do tutorial` and `17.09.19` respectively.

### 2.5. Finding all tasks containing any keyword: `find`

Finds tasks that contain any of the given keywords.<br>
Format: `find KEYWORD...`

> * The search is not case sensitive. e.g `lunch` will match `Lunch`
> * The order of the keywords does not matter. e.g. `Lunch Plan` will match `Plan Lunch`
> * Tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `Lunch` will match `Lunch Plan`

Examples:

* `find Lunch`<br>
  Returns `Lunch Plan` and `lunch`
* `find Breakfast Lunch Dinner`<br>
  Returns any task having names `Breakfast`, `Lunch`, or `Dinner`

### 2.6. Deleting a task : `delete`

Deletes the specified task from the todolist. <br>
Format: `delete INDEX [PARAMETER]...`

> * Deletes the task at the specified `INDEX`. <br>
    The index refers to the index number shown in the last task listing.
    Index **must include the type of task**, e.g. f1 for 1st float task and **must be followed by a positive integer** 1, 2, 3, ... <br>
> * If PARAMETER are given, then the specified task parameter would be deleted
> The parameter refers to the prefix used to indicate the tasks parameters

Examples:

* `list`<br>
  `delete f2`<br>
  Deletes the 2nd float task in the todolist.
* `list`<br>
  `delete e4 /from /description`
  Deletes the end time and description of the 4th event task in the todolist.

**Note: If select was the previous command, index is not needed.**

###2.7. Complete a task: `done`

Marks the task as complete and put in under the completed list. <br>
Format: `done INDEX`

> Mark the task at the specified `INDEX` as complete.<br>
> The index refers to the index number shown in the most recent listing.<br>
    The index refers to the index number shown in the last task listing.
    Index **must include the type of task**, e.g. f1 for 1st float task and **must be followed by a positive integer** 1, 2, 3, ... <br>

Examples:

* `list`<br>
  `done d2`<br>
  Marks the 2nd deadline task in the todolist as complete.

### 2.8. Select a tasks: `select`

Selects the tasks identified by the index number used in the last task listing.<br>
Format: `select INDEX...`

> Selects the task at the specified `INDEX`.<br>
> The index refers to the index number shown in the most recent listing.<br>
    The index refers to the index number shown in the last task listing.
    Index **must include the type of task**, e.g. f1 for 1st float task and **must be followed by a positive integer** 1, 2, 3, ... <br>

Examples:

* `list`<br>
  `select e2`<br>
  Selects the 2nd event task in the todolist.
* `list`<br>
  `select f1-f5` or `select f1-5` or `select f5-1`<br>
  Selects the 1st to 5th todo task in the todolist.
* `list`<br>
  `select e2, d4`<br>
  Selects the 2nd event task and 4th deadline task in the todolist.

Note: It is not necessary to type in the select command **if the index of the task is given**

### 2.9. Undoing previous command : `undo`

Undo previous command
Format: `undo`

### 2.10. Clear ToDoList: `clear`

Clear all the tasks in the ToDoList
Format: `clear`

### 2.11. Exiting the program : `exit`

Exits the program.<br>
Format: `exit`

###2.12. Saving the data: `save`

Save a copied data file in another location.
Format: `save FILE_PATH`

Examples:

* `save C:/Users/Computing/Desktop/CS2103`<br>
  A new copied data file will be created in C:/Users/Computing/Desktop/CS2103.

**Note: **
ToDoList data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

###2.13. Changing the data file location: `changedir`

Loads the data and set data storage location to filePath.
Format: `changedir FILE_PATH`

Examples:

* `changedir C:/Users/Computing/Desktop/CS2103`<br>
  Loads the data and set data storage location to C:/Users/Computing/Desktop/CS2103.

###2.14. Changing the data file location: `import`

Imports tasks from the data file specified.
Format: `import FILE_PATH`

> * The file in the specified file path should be an xml file 
> * The path can be relative or absolute addressing

Examples:

* `import C:/Users/Computing/Desktop/CS2103`<br>
  Import the tasks from the data file in C:/Users/Computing/Desktop/CS2103.

## 3. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous todolist folder. <br>

## 4. Command Summary

* **Add**  `add TITLE /venue [VENUE] /on [DATE] /from [DATE_TIME] /to [DATE_TIME] /by [DATE_TIME] /level [DATE_TIME] /description [DESCRIPTION] #[TAG]...` <br>
  e.g. add Do Tutorial /venue CLB /from today 3pm /to today 5pm /level 2 /description for week13 #CS2103

* **Clear** : `clear` <br>
   e.g.

* **Changedir** : `changedir FILE_PATH` <br>
   e.g. changedir C:/Users/Computing/Desktop/CS2103

* **Delete** : `delete INDEX [PARAMETER]...` <br>
   e.g. delete f3
   e.g. delete f3 /description

* **Done** : `done INDEX` <br>
   e.g. done d3

* **Edit** : `edit INDEX /title [TITLE] /venue [VENUE] /on [DATE] /from [DATE_TIME] /to [DATE_TIME] /by [DATE_TIME] /level [LEVEL] /description [DESCRIPTION] #[TAG]...` <br>
e.g. edit f7 /title buy calculator

* **Exit** : `exit` <br>
e.g.

* **Find** : `find KEYWORD...` <br>
  e.g. find tutorial quiz

* **Help** : `help [COMMAND]` <br>
  e.g. help add
   
* **Import** : `import FILE_PATH` <br>
   e.g. import C:/Users/Computing/Desktop/CS2103

* **List** : `list /from [DATE_TIME] /to [DATE_TIME]` <br>
  e.g. list /from 7 April

* **Save** : `save FILE_PATH` <br>
   e.g. save C:/Users/Computing/Desktop/CS2103

* **Select** : `select INDEX...` <br>
  e.g. select e2
  e.g. select f1-f5
  e.g. select d7, e9, f3

* **Undo** : `undo` <br>
e.g.
