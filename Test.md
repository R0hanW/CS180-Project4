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
 1. Username: "test1234"
 2. Password: "test1234"
 3. Click Login

## Test 1: Create Courses as Teacher
 1. Click Create Course,
 2. Course Name: "CS193",
 3. Allow Students to create posts: yes,
 4. Click Submit

Expected Output: Course Created

## Create Posts
 1. Click Create Post,
 2. Course Topic: "Homework",
 3. Post Description: "Complete Homework",
 4. Create poll: yes,
 5. Click Submit
 6. Click Create Post,
 7. Course Topic: "Test",
 8. Post Description "Complete Test",
 9. Create poll: no,
 10. Click Submit

Expected Output: Post Created

## Test 2: Home, Back, and Forward Button
1. Click Home Button,
2. Click CS193,
3. Click Home Button,
4. Click Back Button,

Expected Output: Back at the Course Screen

## Test 3: Edit Account
 1. Click John,
 2. Click Edit Account,
 3. Changee Name: "Jane",
 4. Chang Username: "test12345",
 5. Are you a teacher: yes,
 6. Password: "test12345",
 7. Confirm Password: "test12345",
 8. Click Sign Up

Expected Output: New Username and Password

## Test 4: Logout
 1. Click Jane,
 2. Click Logout

Expected Output: Back at the default login page

## Login and Sign up as a student

## Sign Up
 1. Click Sign Up
 2. Name: "Alex",
 3. Username "student1",
 4. Password: "student1",
 5. Confirm Password: "student1",
 6. Are you a teacher: no,
 7. Click Sign Up

Expected Output: A main menu with the name 'Alex' at the top right and a create course button under the name.

## Login
 1. Username: "student1",
 2. Password: "student1",
 3. Click Login

## Create Posts
 1. Click CS193,
 2. Click Create Post,
 3. Course Topic: "Homework Help",
 4. Post Description: "I need Help",
 5. Create poll: yes,
 6. Click Add Poll Option,
 7. Option1: "HW1",
 8. Click Add Poll Option,
 9. Option2: "HW2",
 10. Click Submit,

Expected Output: Post Created

## Create Comment
 1. Click HomeWork Help,
 2. Comment: "I will Help you",
 3. Click Submit

## Reply
 1. Click Thumbs Up,
 2. Click Reply,
 3. Comment: "Thanks",
 4. Click Submit

## Vote In Poll
 1. Click View Poll, 
 2. Choose an option,
 3. Click "OK"

## Delete Account
 1. Click Alex,
 2. Click Delete Account,
 3. Try to relogin with,
 4. Username: "student1",
 5. Password: "student1"

Expected Output: Back at default login page - if you log in with a different account, all posts/comments created by that user will be deleted



