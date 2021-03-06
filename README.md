# Pre-work - Simple ToDo

Simple ToDo is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: zhuqicn

Time spent: 15 hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] User can **successfully add and remove items** from the todo list
* [x] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list.
* [x] User can **persist todo items** and retrieve them properly on app restart

The following **optional** features are implemented:

* [x] Persist the todo items [into SQLite](http://guides.codepath.com/android/Persisting-Data-to-the-Device#sqlite) instead of a text file
* [x] Improve style of the todo items in the list [using a custom adapter](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView)
* [x] Add support for completion due dates for todo items (and display within listview item)
* [x] Use a [DialogFragment](http://guides.codepath.com/android/Using-DialogFragment) instead of new Activity for editing items
* [x] Add support for selecting the priority of each todo item (and display in listview item)
* [ ] Tweak the style improving the UI / UX, play with colors, images or backgrounds

The following **additional** features are implemented:

* [ ] List anything else that you can get done to improve the app functionality!

## Video Walkthrough 

Here's a walkthrough of implemented user stories:

![simple todo](https://cloud.githubusercontent.com/assets/6313395/12062050/d0eb1e18-af49-11e5-8bf6-5601722c54ad.gif)
GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Note
* When using custom `ListAdapter`, I found the click/long click handler for list items did not work any more. After researching, I found the root cause is the newly added CheckBox widget within the ListView row. Turns out the CheckBox is **eating** those events. The solution is to mark the CheckBox with `android:focusable="false"`, and it works fine.
* The DatePicker widget is causing me some trouble as well. the `getYear()` value is off by 1900. Guess I should use `Calendar` or something similar instead of deprecated `Date` conversion.
