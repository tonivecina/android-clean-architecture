# Android clean architecture

This guide will show how to build a Android project with a clean architecture. It's very important we have a project with packages and subpackages ordered by services, implementations and android functionalities.

## Table of contents

* [Packages](#packages)
	* [Activities](#activities)
	* [Configuration](#configuration)
	* [Entities](#entities)
	* [Patterns](#patterns)
	* [Services](#services)
	* [Views](#views)
* [Files](#files)
	* [Naming](#naming)
* [Classes](#classes)

## Packages

The packages and subpackages must be ordered alphabetically for a fast localization (Automatic function of Android Studio). The valid packages will show hereunder.

### Activities

Activities package must contains every activity of project divided in sub-packages. Each package will have Activity class and other class with services and implementations. If the activity has fragments, this fragments will be contained in sub-packages inside of this package.

Each interface used by Activity or Fragment must be separated by class (as service) and file. Please, see [Files category](#files).

This packages must be named with Activity name or Fragment name without key '*Activity*' or '*Fragment*'. For example, package of **MainActivity** will be *main* or  **LoginFragment** will be *login*.

![](https://raw.githubusercontent.com/tonivecina/android-clean-architecture/master/images/screen_package_activities.png)

#### Which are the services files allowed?

To define navigations will use *NavigationService*. Other files also allowed when its function is very defined like network services, location services... Please, you must not mix functionalities in services files, only call between them through Activity or Fragment. When it's necessary functions mixed, you must use **ResourcesService**.

Native Activity functions as OnActivityResults, OnPermissionsResults... will be services too.

This is a full Login Activity:

![](https://raw.githubusercontent.com/tonivecina/android-clean-architecture/master/images/screen_package_activities_full.png)

#### How to define services or implementations?

For example, if the activity or fragment contains buttons and this buttons needs to define actions, we will use OnClickListener. Please, don't include this implementation directly in Activity or Fragment class. We create new class and file as service and this interface will be included here.

This is an example of LoginFragment of the capture on top.

```android
public class LoginFragment extends Fragment {

    private LoginFragmentOnClickListener mOnClickListenerService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        synchronized (this) {
            mOnClickListenerService = new LoginFragmentOnClickListener(this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button loginButton = (Button) view.findViewById(R.id.fragment_login_button);
        loginButton.setOnClickListener(mOnClickListenerService);
    }
}
```

In every constructor of each service class will be included Activity or Fragment referred, **only if it's necessary**.

This example corresponds to LoginFragmentOnClickListener service.

```android
class LoginFragmentOnClickListener implements View.OnClickListener {

    private LoginFragment mLoginFragment;

    LoginFragmentOnClickListener(LoginFragment fragment) {
        mLoginFragment = fragment;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.fragment_login_button:
                onClickLogin();
                break;

            default:
                break;
        }
    }

    private void onClickLogin() {
        ...
    }
}
```

More info of class structures in [classes](#classes) section.


### Configuration

Configuration package contains Configuration files that it's Application class (used as Singleton) of the project. This class is used during all app cycle with services like user session, local parameters required... ActivityLifecycleCallbacks management can be implemented here for application status control.

This is a simple Configuration example:

```android
final public class Configuration extends Application {

    private static Configuration mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;

        DLog.success("Application is ready!");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    //region GettersReg
    
    public static synchronized Configuration get() { return mInstance; }
    
    //endregion
}
```


### Entities

Models and entities must be contained in this package. If an entity needs to use SharePreferences like credentials, the class entity would must be:

```android
final public class Credentials {
    private final String PREFERENCES_NAME = "credentials";

    private final String BUNDLE_EMAIL = "email";
    private final String BUNDLE_PASSWORD_HASH = "passwordHash";
    private final String BUNDLE_TOKEN = "token";

    private SharedPreferences mPreferences;

    public Credentials() {
        Context context = Configuration.get();

        synchronized (this) {
            mPreferences = context
                    .getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        }
    }

    //region GettersReg
    @Nullable
    public String getEmail() {
        return mPreferences.getString(BUNDLE_EMAIL, null);
    }

    @Nullable
    public String getPasswordHash() {
        return mPreferences.getString(BUNDLE_PASSWORD_HASH, null);
    }

    @Nullable
    public String getToken() {
        return mPreferences.getString(BUNDLE_TOKEN, null);
    }

    public boolean isLogged() {
        return getEmail() != null && getPasswordHash() != null && getToken() != null;
    }
    //endregion

    //region SettersReg
    public void set(final String email, final String passwordHash) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(BUNDLE_EMAIL, email);
        editor.putString(BUNDLE_PASSWORD_HASH, passwordHash);
        editor.apply();
    }

    public void set(final String token) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(BUNDLE_TOKEN, token);
        editor.apply();
    }
    //endregion
}
```

### Patterns

The global patterns that it can be used at any class are contained here like Boolean, String, etc. File of class must be named equal to object name; String, Boolean...

![](https://raw.githubusercontent.com/tonivecina/swift-clean-architecture/master/images/screen_folder_extensions.png)

#### How define navigations?

In common use of UIViewController extension is to define with arguments for a navigation. For example:

```Swift
extension UIViewController {

    /// This function initialize DetailViewController and set Origin value.
    ///
    /// - Returns: DetailViewController.

    static func detailViewController(origin: String) -> DetailViewController {
        let viewController = DetailViewController(nibName: "DetailViewController", bundle: nil)
        viewController.origin = origin

        return viewController
    }

    /// This variable initialize FormViewController.
    ///
    /// - Returns: FormViewController.

    static var formViewController: FormViewController {
        return FormViewController(nibName: "FormViewController", bundle: nil)
    }

}
```

### Services

The services folder must contain subfolder and the class files to execute services like Api connections, CoreLocation...

Please, you must not use services like CoreLocation, CoreData (and its protocols)... in ViewControllers. The best is create a global service with manage control, the ViewControllers will use this services.

This is a simple service to write in console under debug mode.

```Swift
public struct Log {

    /// This method set message in console under debug mode.
    ///
    /// - Parameter message: This string is set in console.

    fileprivate static func set(_ message: String) {

        #if DEBUG
            debugPrint(message,
                       separator: "\n-----------------------\n",
                       terminator: "\n-----------------------\n")
        #endif
    }
}

extension Log {

    /// This method set message in console with *Success* prefix.
    ///
    /// - Parameter message: This string is set in console.

    public static func success(_ message: String) {
        return set("SUCCESS: " + message)
    }

    /// This method set message in console with *Error* prefix.
    ///
    /// - Parameter message: This string is set in console.

    public static func error(_ message: String) {
        return set("ERROR: " + message)
    }

    /// This method set message in console with *Process* prefix.
    ///
    /// - Parameter message: This string is set in console.

    public static func process(_ message: String) {
        return set("PROCESS: " + message)
    }

    /// This method set message in console with *Warning* prefix.
    ///
    /// - Parameter message: This string is set in console.

    public static func warning(_ message: String) {
        return set("WARNING: " + message)
    }
}
```

#### how do it use?

In any class you can set log in a single line.

```Swift
Log.success("Application is ready!")
```

### ViewControllers

This folder contains all ViewControllers of project. You must create a subfolder for each ViewController. This subfolder will contain the ViewController class, xib file and the files segmented by ViewController extension.

Each protocol used by ViewController must be separated by an extension and file. Please, see [Files category](#files).

![](https://raw.githubusercontent.com/tonivecina/swift-clean-architecture/master/images/screen_folder_viewcontrollers.png)

In addition to protocols, this are a valid files:

#### Configurations

In this file/extension defines enums, static variables, private structs, etc. used by ViewController.

```Swift
extension FormViewController {

    enum TextFieldTag: Int {
        case email = 0
        case password = 1
    }

    struct Form {
        var email: String?
        var password: String?
    }

    enum FormError: Error {
        case reason(detail: String)
        case unknown
    }
}
```

#### IBActions

This extension contains all IBActions that UIView needs.

```Swift
extension FormViewController {

    @IBAction private func pressSubmit(_ sender: UIButton) {
        view.endEditing(true)
        
        do {
            try setCredentials(email: form.email, password: form.password)

            pushDetailViewController()

        } catch FormError.reason(let detail) {
            presentErrorAlertController(detail)

        } catch {
            presentErrorAlertController("An error occurred while try to set credentials. Try again.")
        }
    }
    
    @IBAction private func ...
    ...

}
```

#### Navigations

All navigations produced in the ViewController must be contained here. In addition, each function must be begin by ***present*** or ***push*** *(depending of navigation type)*.

```Swift
extension FormViewController {

    func presentErrorAlertController(_ message: String) {
        let alertController = UIAlertController(title: "Error", message: message, preferredStyle: .alert)

        let action = UIAlertAction(title: "Ok", style: .default, handler: nil)
        alertController.addAction(action)

        present(alertController, animated: true, completion: nil)
        Log.error(message)
    }

    func pushDetailViewController() {
        let origin = String(describing: FormViewController.self)

        let viewController = UIViewController.detailViewController(origin: origin)
        navigationController?.pushViewController(viewController, animated: true)

        Log.process("DetailViewController will be pushed from " + origin)
    }
}
```

#### Protocols

It the ViewControllers use protocols like UITextFieldDelegate, UITableViewDataSource, UITableViewDelegate... This extensions are categorized as Protocol extension and separated by files with *View associated* plural name. For example, **UITableViewDataSource** and **UITableViewDelegate** extensions must be contained in *ViewController+UITableViews* file or **UITextFieldDelegate** in *ViewController+UITextFields* file.

```Swift
extension FormViewController: UITextFieldDelegate {

    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        textField.resignFirstResponder()
        
        return true
    }

    func textFieldDidBeginEditing(_ textField: UITextField) {

        if let loginTextField = textField as? LoginFormTextField {
            loginTextField.isHighlighted = true
        }

    }

    func textFieldDidEndEditing(_ textField: UITextField) {

        guard let tag = TextFieldTag(rawValue: textField.tag) else {
            fatalError("Textfield not defined.")
        }

        switch tag {

        case .email:    form.email = textField.text
        case .password: form.password = textField.text

        }

        if let loginTextField = textField as? LoginFormTextField {
            loginTextField.isHighlighted = false
        }
    }
}
```

#### Resources

This extension contains all methods not contains in previous extensions. Methods or variables like setters, getters, dispatchers...

#### Service

If the ViewController uses a service contained in **Services** folder or the service is very defined, this extensions will contain refered methods. For example, if *Service* folder has Location service, this extension file must be called *ViewController+LocationService*.

Another example for credentials management:

```Swift
extension FormViewController {

    func setCredentials(email: String?, password: String?) throws {

        guard let email = email else {
            throw FormError.reason(detail: "Email not found.")
        }

        guard Bool.isEmailValid(email) else {
            throw FormError.reason(detail: "Email is invalid.")
        }

        guard let password = password else {
            throw FormError.reason(detail: "Password not found.")
        }

        guard password.characters.count > 3 else {
            throw FormError.reason(detail: "Password must contains more than 3 characteres.")
        }

        let credentials = Credentials()

        guard credentials.set(email: email, passwordHash: password) else {
            throw FormError.unknown
        }

        Log.success("Credentials were stored.")
    }

}
```

### Views

The views classes must be ordered in subfolders and the name file ends with view type. For example, a textfield for forms would can called **FormTextField** inside of *UITextfields* folder.

![](https://raw.githubusercontent.com/tonivecina/swift-clean-architecture/master/images/screen_folder_views.png)

This is a full structure of project ordered in folders, subfolders and files:

![](https://raw.githubusercontent.com/tonivecina/swift-clean-architecture/master/images/screen_folder_project.png)

## Files

### Naming

The files name also must have structure. We are going to order it in two categories; *Simple* files and *Extension* files.

#### Simple files

Its name must be descriptive for its function, not apply insignificant logical and please, name not greater to 25 characters.

When the file contains typed class like UIViewController, UIButton, UITextfield, etc. Its structure must be: ***simple name*** + ***class type***. For example, DetailViewController, LoginViewController, BoldButton, FormTextField...

![](https://raw.githubusercontent.com/tonivecina/swift-clean-architecture/master/images/screen_files_detailviewcontroller.png)

#### Extension files

This files contains extensions of other classes and it must be named with a structure like *[parent class file name]* + *[+ symbol]* + *[service or descriptive function]*. For example, **FomViewController** contains UITextFieldDelegate protocol for the UITextFields contained in view, so this protocol is contained in a file with a extension of FormViewController and its name would be *FormViewController+UITextFields*. A similar case would be with UITableViewDataSource and UITableViewDelegate, its file would be *FormViewController+UITableViews*.

![](https://raw.githubusercontent.com/tonivecina/swift-clean-architecture/master/images/screen_files_formviewcontroller.png)

## Classes and Structures

Please, for a correct and clean code follow [The Official raywenderlich.com Swift Style Guide](https://github.com/raywenderlich/swift-style-guide)