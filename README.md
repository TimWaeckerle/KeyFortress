# Password Manager

This is a simple password manager designed to help users securely manage their passwords. The program was developed using Java and JavaFX, providing a user-friendly interface for managing password keystores.

## Features

- Adding, deleting, and editing password keystores
- Saving and encrypting passwords in keystores
- Calculating password strength based on various criteria
- Support for multiple users with separate keystores

## Installation

1. Make sure you have Java installed on your system.
2. Clone this repository to your local computer.
3. Open the project in your preferred development environment.
4. Run `mvn clean install`.
5. Run the main program (Main.java) to start the application.

## Usage

### Login
Upon initial launch, you need to create a user. To do this, click the "Register" button in the UI.
![Register](https://github.com/TimWaeckerle/KeyFortress/assets/97874901/a5732920-ad40-459c-9901-cfb0c359e655)

Please enter your desired username and password in the new window. 
For your account password, there is a restriction: it must be at least 6 characters long and must contain a number.
Afterward, you can log in with your previously created user.

### Keystore Overview
Once logged in, you'll see the main view of the application, with the title displaying your current user. If you've just created an account or haven't created a keystore yet, you'll only see an empty screen.
At the bottom of the window, you'll find the "Add Keystore" button. 

![Add Keystore](https://github.com/TimWaeckerle/KeyFortress/assets/97874901/feda85fb-50a2-4ed5-a770-9469a1d89884)

Upon pressing it, you can create your first keystore in another window. You'll need to choose a name and another password. 
For your keystore password, there is another restriction: it must be at least 8 characters long and must contain a number and a special character. 
Make sure you remember your password! There is no way to access the keystore without entering the corresponding password.

After you've created your keystore, two things happen:

1. Change in Keystore Overview
You'll notice a change in your keystore overview.
![Keystore Overview](https://github.com/TimWaeckerle/KeyFortress/assets/97874901/187cb61f-b610-49aa-870a-136b8fc05457)

Your newly created keystore will be visible in the view. Next to it, there's a label indicating how strong the passwords inside your keystore are overall.
Depending on four criteria: length, capital letters, special characters, and numbers. 
Depending on the rating of your keystore's safety, the color of the label changes from red to yellow to green. 
Green means your passwords are safe. Yellow indicates your passwords are okay but you should consider changing them. Red indicates that you should change them immediately.

To enter an existing keystore, press the button of the keystore and type in the password. Afterwards, the Keystore view will be started.

2. Keystore View
After creating a keystore, the keystore view will open automatically.

### Keystore View
Inside the keystore view, you'll see your keystore entries. If you've just created a keystore, there will be no entries yet.
To create entries, you can use the "Add Entry" button at the bottom.

![Add Entry](https://github.com/TimWaeckerle/KeyFortress/assets/97874901/7e3d81e4-38b7-4ef0-87e0-75a26029cb84)

As the buttons already indicate, you are able to leave the current keystore. This means you are closing it and need to re-enter the password if you want to open it again.
And you are able to delete the whole keystore including all entries.

Once you press the "Add Entry" button, another window will ask you to enter your entry name and corresponding password. 
There are no restrictions regarding length, numbers, or special characters. The only restriction is that both fields can't be empty.

After you've created your entry, it will be shown inside the keystore view.
![Entry](https://github.com/TimWaeckerle/KeyFortress/assets/97874901/9e247032-fbfa-46d5-9fa5-d9468affdfd0)

For each entry, you have three options: you can change the values of it via the "Edit" button or delete it via the "Delete" button.
You are also able to copy the saved password to your clipboard via the "Copy Password" button.

## Contributing

Contributions are welcome! Feel free to fork the repository and submit pull requests to suggest improvements or new features.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
