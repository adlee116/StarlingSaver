# Starling Saver - Aidan Lee

## Pre-requisites
1. To ensure this application will work correctly, you will need the following credentials for the sandbox user to collect the token, if it has expired.
Username: adlee116@mail.com 
Password: Qwerty123!
Please update the token using the variable 'sessionsToken' in the TokenInterceptor class. This was done to bypass the need for the auth implementation
2. Please ensure you run in debug, as it will allow you to see chuck. Giving you a view of all requests. 

## Architecture
Language: Kotlin
UI: XML
Pattern: MVI (Single entry point for the view model, using a state to drive major UI changes. Smaller changes, such as errors are collected through flows form the view model as well)
Tests: Areas supported with tests. 
1. Repository level (Example: TransactionsRepositoryImplTest)
2. Use case level (Example: GetTransactionsUseCaseTest)
3. View model level (Example: TransactionsViewModelTest)

# Still left to do
I will be updating this project when I get a little more time but wanted it uploaded to ensure you had something to see. 
Things still left to take care of. 
1. Main state in the view model, reading etc. Should not be of mutableFlow type because this flow will, once emitted, no longer have a memory of what it has passed and therefore we cannot use it to check the state again if we need to. 
We would use mutableState instead. 
2. More tests to be added. Repository failure path should be added. Mock failure of use case to be added to ensure we don't just test the happy path. 
3. Extract some of the logic from the viewModel which builds the list items so it can be more specifically tested. 
4. Mapper added to the repositories, so that we can better handle the return values, instead of throwing the CodeException. 
5. Take out some of the hard coded stuff, such as currency types. Add functions that directly use currency type to ensure we don't call the same calculations on different country currency.
6. Modularisation of the project. Unsure of the best practise here. Some move network / domain etc. Some move features. I prefer features. Better for larger teams working in different parts.  
