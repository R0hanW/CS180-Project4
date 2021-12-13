# Testing

## Login and Sign up as Teacher

## Sign Up
 1. Click Sign Up
 2. Input Name: "John",
 3. Input Username "test1234",
 4. Input Password: "test123",
 5. Input Confirm Password: "test1234",
 6. Select Are you a teacher: yes,
 7. Client will be redirected to the Login Page
 8. Input Username "test1234",
 9. Input Password: "test123",
 10. Click Login

Expected Output: A main menu with the name 'John' at the top right and a create course button under the name.

## Login
Username: "test1234",
Password: "test1234",
Click Login

## Test 1: Create Courses as Teacher
Click Create Course,
Course Name: "CS193",
Allow Students to create posts: yes,
Click Submit

## Create Posts
Click Create Post,
Course Topic: "Homework",
Post Description: "Complete Homework",
Create poll: yes,
Click Submit

Click Create Post,
Course Topic: "Test",
Post Description "Complete Test",
Create poll: no,
Click Submit

## Test 2: Home, Back, and Forward Button
Click Home Button,
Click CS193,
Click Home Button,
Click Back Button,

## Test 3: Edit Account
Click John,
Click Edit Account,
Changee Name: "Jane",
Chang Username: "test12345",
Are you a teacher: yes,
Password: "test12345",
Confirm Password: "test12345",
Click Sign Up

## Test 4: Logout
Click Jane,
Click Logout

## Login and Sign up as a student

## Sign Up
Click Sign Up

Name: "Alex",
Username "student1",
Password: "student1",
Confirm Password: "student1",
Are you a teacher: no,
Click Sign Up

## Login
Username: "student1",
Password: "student1",
Click Login

## Create Posts
Click CS193,
Click Create Post,
Course Topic: "Homework Help",
Post Description: "I need Help",
Create poll: yes,
Click Add Poll Option,
Option1: "HW1",
Click Add Poll Option,
Option2: "HW2",
Click Submit,

## Create Comment
Click HomeWork Help,
Comment: "I will Help you",
Click Submit

## Reply
Click Thumbs Up,
Click Reply,
Comment: "Thanks",
Click Submit

## Vote In Poll
Click View Poll, 
Choose an option,
Click "OK"

## Delete Account
Click Alex,
Click Delete Account,
Try to relogin with,
Username: "student1",
Password: "student1"



