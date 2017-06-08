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

More info of class structures in [classes](#classes) section.


### Configuration

Configuration package contains Configuration files that it's Application class (used as Singleton) of the project. This class is used during all app cycle with services like user session, local parameters required... ActivityLifecycleCallbacks management can be implemented here for application status control.

Please, check [configuration](#configuration_file) file section.


### Entities

Please, check [entities](#entities_files) file section.


### Patterns

The global patterns that it can be used at any class are contained here like Boolean, String, etc. File of class must be named equal to object name; String, Boolean...

![](https://raw.githubusercontent.com/tonivecina/android-clean-architecture/master/images/screen_package_patterns.png)

Please, check [patterns](#patterns_files) file section.

### Services

The services package must contains subpackages and the class files to execute services like Api connections, Location...

Please, you must not implement services like Location, Bus, Tracking... in Activities. The best is create a global service with manage control, the Activities will use this services.

Please, check [services](#services_files) files section.

### Views

The views classes must be ordered in subpackages and the name file ends with view type. For example, a editText for forms would can called **FormEditText** inside of *EditTexts* package.

![](https://raw.githubusercontent.com/tonivecina/android-clean-architecture/master/images/screen_package_views.png)

This is a simple full structure of project ordered in packages, subpackages and files:

![](https://raw.githubusercontent.com/tonivecina/android-clean-architecture/master/images/screen_project_packages.png)

## Files

### Naming

The files name also must have structure. We are going to order it in two categories; *Activity* files and *Service* files.

#### Activity files

Its name must be descriptive for its function, not apply insignificant logical and please, name not greater to 25 characters. When the file contains typed class like Activity, Fragment, Button, EditText, etc. 

Its nomenclature must be:

```
SIMPLE NAME + CLASS TYPE
```

* *Main****Activity***
* *Login****Fragment***
* *Bold****Button***
* *Form****EditText***
* ...

#### Service files

This files are classes used as services of other classes. For example, if an Activity named MainActivity needs to implement OnClickListener interface, we will create new file: **MainActivityOnClickListener**.

Methods like *OnActivityResults*, *OnRequestPermissionsResults*... inside of Activity class are categorized here too.

Its nomenclature must be:

```
PARENT CLASS NAME + INTERFACE OR METHOD NAME
```

* *MainActivity****OnScrollListener***
* *LoginFragment****OnClickListener***
* *GoalActivity****OnActivityResults***
* *RequestActivity****OnRequestPermissionsResults***
* ...


When a service class needs a service inside in other package, for example the Services package.

Its nomenclature must be:

```
PARENT CLASS NAME + SERVICE NAME + SUBSERVICE PACKAGE NAME
```

* ***MapActivity****Location****Service***
* ...

Service termination also is used when service class comply with a strong function, for example navigations.

Its nomenclature must be:

```
PARENT CLASS NAME + SERVICE NAME + Service
```

* ***LoginFragment****Credentials****Service***
* ***UsersActivity****Ranking****Service***
* ...


![](https://raw.githubusercontent.com/tonivecina/android-clean-architecture/master/images/screen_package_activities_full.png)

## Classes

### Activities and fragments

### Activities services

#### How to define services or implementations?

For example, if the activity or fragment contains buttons and this buttons needs to define actions, we will use OnClickListener. Please, don't include this implementation directly in Activity or Fragment class. We create new class and file as service and this interface will be included here.

This is a simple example of LoginFragment:

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

### Configuration file

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

***Not forget to include the Configuration refer in Manifest file***.

```xml
<application
        android:name=".Configuration.Configuration"
        android:allowBackup="true"
        ...>
        
        ...
        
</application>
```

### Entities files

Models and entities must be contained in *Entities* package. If an entity needs to use SharePreferences like credentials, the class entity would must be:

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
    
    public void clear() {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.remove(BUNDLE_EMAIL);
        editor.remove(BUNDLE_PASSWORD_HASH);
        editor.remove(BUNDLE_TOKEN);
        editor.apply();
    }
    //endregion
}
```

#### How to use it?

```android
Credentials credentials = new Credentials();
credentials.set("me@email.com", "myPassword");
credentials.set("MyToken123456789");

if (credentials.isLogged()) {
	String email = credentials.getEmail();	DLog.success("My email is: " + email);   // My email is: me@email.com
	DLog.success("User logged: " + String.valueOf(credentials.isLogged()));    // User logged: true
	
	credentials.clear();
}

DLog.warning("User logged: " + String.valueOf(credentials.isLogged()));    // User logged: false

```

### Patterns files

This is an example of boolean:

```android
final public class Boolean {

    public static boolean isValidEmail(CharSequence email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
```

#### How to use it?

```android
String email = "me@email.com";
boolean isValidEmail = Boolean.isValidEmail(email);    // true
```

### Services files

This is a simple service to write in console under debug mode.

```android
final public class DLog {

    private enum Type {
        DEBUG,
        ERROR,
        INFO,
        WARNING
    }

    final private static String SIGNATURE = "Clean Architecture";

    private static void set(final String message, final Type type) {

        if (!BuildConfig.DEBUG) return;

        String separator = "----------------------------------------";

        switch (type) {
            case DEBUG:
                Log.d(SIGNATURE, message);
                Log.d(SIGNATURE, separator);
                break;

            case ERROR:
                Log.e(SIGNATURE, message);
                Log.e(SIGNATURE, message);
                break;

            case INFO:
                Log.i(SIGNATURE, message);
                Log.i(SIGNATURE, message);
                break;

            case WARNING:
                Log.w(SIGNATURE, message);
                Log.w(SIGNATURE, message);
                break;
        }
    }

    public static void success(final String message) {
        set(message, Type.DEBUG);
    }

    public static void error(final String message) {
        set(message, Type.ERROR);
    }

    public static void info(final String message) {
        set(message, Type.INFO);
    }

    public static void warning(final String message) {
        set(message, Type.WARNING);
    }
}
```

#### how to use it?

In any class you can set log in a single line.

```android
DLog.success("Application is ready!")
```

## Guidelines

* [Code Style for Contributors](https://source.android.com/source/code-style)
* [Ribot Project guidelines](https://github.com/ribot/android-guidelines/blob/master/project_and_code_guidelines.md)