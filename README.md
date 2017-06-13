# Android clean architecture

This guide will show how to build a Android project with a clean architecture. It's very important we have a project with packages and subpackages ordered by services, implementations and android functionalities.

## Table of contents

* [Packages](#packages)
	* [Activities](#activities-package)
	* [Configuration](#configuration-package)
	* [Entities](#entities-package)
	* [Patterns](#patterns-package)
	* [Services](#services-package)
	* [Views](#views)

* [Activities and fragments](#activities-and-fragments)
	* [View](#view)
	* [Processor](#processor)
	* [Listener](#listener)
	* [Routes](#routes)
	* [Interactor](#interactor)

* [Entities](#entities)
	* [Local](#local-entity)
	* [Dynamic](#dynamic-entity)

* [Patterns](#patterns)

* [Services](#services)

* [Views](#views)

## Packages

The packages and subpackages must be ordered alphabetically for a fast localization (Automatic function of Android Studio). The valid packages will show hereunder.

### Activities package

Activities package must contains every activity of project divided in sub-packages. Each package will have Activity class and other class with services and implementations. If the activity has fragments, this fragments will be contained in sub-packages inside of this package.

Each interface used by Activity or Fragment must be separated by class (as service) and file.

This packages must be named with Activity name or Fragment name without key '*Activity*' or '*Fragment*'. For example, package of **MainActivity** will be *main* or  **LoginFragment** will be *login*.

![](https://raw.githubusercontent.com/tonivecina/android-clean-architecture/master/images/screen_package_activities.png)


### Configuration package

If you wish manage application class of your project, Configuration file is your file and it will be contained in this package.

![](https://raw.githubusercontent.com/tonivecina/android-clean-architecture/master/images/screen_package_configuration.png)

### Entities package

All models and entities must be contained here.

![](https://raw.githubusercontent.com/tonivecina/android-clean-architecture/master/images/screen_package_entities.png)

### Patterns package

The global patterns that it can be used at any class are contained here like Boolean, String, etc. File of class must be named equal to object name; String, Boolean...

![](https://raw.githubusercontent.com/tonivecina/android-clean-architecture/master/images/screen_package_patterns.png)

### Services package

The services package must contains subpackages and the class files to execute services like Api connections, Location...

Please, you must not implement services like Location, Bus, Tracking... in Activities. The best is create a global service with manage control, the Activities will use this services.

<img src="https://raw.githubusercontent.com/tonivecina/android-clean-architecture/master/images/screen_package_services.png" width="400">

More info of class structures in [classes](#classes) section.

### Views package

The views classes must be ordered in subpackages and the name file ends with view type. For example, a editText for forms would can called **FormEditText** inside of *EditTexts* package.

<img src="https://raw.githubusercontent.com/tonivecina/android-clean-architecture/master/images/screen_package_views.png" width="400">

This is a simple full structure of project ordered in packages, subpackages and files:

![](https://raw.githubusercontent.com/tonivecina/android-clean-architecture/master/images/screen_project_packages.png)

## Activities and fragments

The Activity and Fragment cycles are segmented with different elements:

### View

View element is the Activity or Fragment class. All UI layer is contained here. It's important that this class doesn't implement interfaces because this package will have Listener elements to do it.

In this class the Process element will be initialized to manage all elements in this pacakge except the view. In addition, It only must contain Getters and Setters for UI.

This is a simple Activity example:

```Android
public class MainActivity extends AppCompatActivity {

    private FrameLayout mContainerFrameLayout;

    private MainActivityProcessor mProcessor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        synchronized (this) {
            mProcessor = new MainActivityProcessor(this);
        }

        mContainerFrameLayout = (FrameLayout) findViewById(R.id.activity_main_frameLayout);

        mProcessor
                .getRoutes()
                .replaceLoginFragment();
    }

    void replace(Fragment fragment) {
        FragmentTransaction fragmentTransaction = mProcessor
                .getView()
                .getFragmentManager()
                .beginTransaction();

        String tag = fragment.getClass().getSimpleName();

        fragmentTransaction.replace(mContainerFrameLayout.getId(), fragment, tag);
        fragmentTransaction.commit();
    }

    //region GettersReg
    public MainActivityProcessor getProcessor() {
        return mProcessor;
    }
    //endregion
}
```

... or Fragment:

```Android
public class LoginFragment extends Fragment {

    private Button mLoginButton;
    private LoginFormEditText mEmailEditText, mPasswordEditText;

    private LoginFragmentProcessor mProcessor;

    public static LoginFragment get() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        synchronized (this) {
            mProcessor = new LoginFragmentProcessor(this);
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

        mEmailEditText = (LoginFormEditText) view.findViewById(R.id.fragment_login_editText_email);
        mPasswordEditText = (LoginFormEditText) view.findViewById(R.id.fragment_login_editText_password);

        LoginFragmentOnClickListener onClickListener = mProcessor.getOnClickListener();

        mLoginButton = (Button) view.findViewById(R.id.fragment_login_button);
        mLoginButton.setOnClickListener(onClickListener);

        mProcessor.onViewCreated();
    }

    //region GettersReg
    String getEmail() {
        return mEmailEditText
                .getText()
                .toString();
    }

    String getPassword() {
        return mPasswordEditText
                .getText()
                .toString();
    }
    //endregion

    //region SettersReg
    void setCredentials(@Nullable String email, @Nullable String password) {
        mEmailEditText.setText(email);
        mPasswordEditText.setText(password);
    }
    //endregion
}
```

### Processor

The Process class will manage all elements in this package. This class is the communication channel with the view. Furthermore all elements in this package except the view will be initialized here.

This is a Process example with Listener and Interactor:

```Android
final class LoginFragmentProcessor {

    private LoginFragment mView;
    private Context mContext;

    private LoginFragmentInteractorCredentials mInteractorCredentials;

    private LoginFragmentOnClickListener mOnClickListener;

    LoginFragmentProcessor(LoginFragment view) {
        mView = view;
        mContext = mView.getContext();

        synchronized (this) {
            mInteractorCredentials = new LoginFragmentInteractorCredentials();

            mOnClickListener = new LoginFragmentOnClickListener(this);
        }
    }

    void onViewCreated() {
        Credentials credentials = mInteractorCredentials.getCredentials();
        mView.setCredentials(credentials.getEmail(), credentials.getPasswordHash());
    }

    //region GettersReg
    LoginFragment getView() {
        return mView;
    }

    LoginFragmentOnClickListener getOnClickListener() {
        return mOnClickListener;
    }
    //endregion

    //region SettersReg
    void setCredentials() {
        try {
            String email = mView.getEmail();
            String password = mView.getPassword();

            mInteractorCredentials.set(email, password);

            if (mContext instanceof MainActivity) {
                MainActivity activity = (MainActivity) mContext;
                activity
                        .getProcessor()
                        .getRoutes()
                        .replaceDetailFragment(mView.getClass().getSimpleName());
            }

        } catch (Exception exception) {
            Toast.makeText(mContext, exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    //endregion
}
```

### Listener

The Listener elements correspond with View listeners. This listeners will not apply in view directly because it's managed by Processor. In this class, interfaces like OnClickListener, OnFocusChangeListeners, OnScrollListener... will be implemented.

Methods like *OnActivityResults*, *OnRequestPermissionsResults*... inside of Activity class are categorized as listener elements too.

Its nomenclature must be:

```
PARENT CLASS NAME + INTERFACE OR METHOD NAME
```

* *MainActivity****OnScrollListener***
* *LoginFragment****OnClickListener***
* *GoalActivity****OnActivityResults***
* *RequestActivity****OnRequestPermissionsResults***
* ...

This is an example for a click listener:

```Android
class LoginFragmentOnClickListener implements View.OnClickListener {

    private LoginFragmentProcessor mProcessor;

    LoginFragmentOnClickListener(LoginFragmentProcessor processor) {
        mProcessor = processor;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.fragment_login_button:
                mProcessor.setCredentials();
                break;

            default:
                break;
        }
    }
}
```

### Routes

All navigations and connections with other activites or fragments are defined here. You apply intents here. Look at this example:

```Android
public final class MainActivityRoutes {

    private MainActivityProcessor mProcessor;

    MainActivityRoutes(final MainActivityProcessor processor) {
        mProcessor = processor;
    }

    public void replaceDetailFragment(final String origin) {
        DetailFragment fragment = DetailFragment.get(origin);

        mProcessor
                .getView()
                .replace(fragment);
    }

    void replaceLoginFragment() {
        LoginFragment fragment = LoginFragment.get();

        mProcessor
                .getView()
                .replace(fragment);
    }
    
    void intentLoginActivity() {
        LoginActivity activity = new LoginActivity();

        Intent intent = new Intent(mProcessor.getView(), LoginActivity.class);
        mProcessor
                .getView()
                .startActivity(intent);
    }
}
```

### Interactor

The Interactors can communicate directly with entities to manage data or services.

```Android
final class LoginFragmentInteractorCredentials {

    //region GettersReg
    Credentials getCredentials() {
        return new Credentials();
    }
    //endregion

    //region SettersReg
    void set(final String email, final String password) throws Exception {

        if (email.isEmpty())
            throw new Exception("Email not found");

        if (!Boolean.isValidEmail(email))
            throw new Exception("Email is invalid");

        if (password.length() < 4)
            throw new Exception("Password must contains more than 3 characters");

        Credentials credentials = getCredentials();
        credentials.set(email, password);

        DLog.success("Credentials were stored.");
    }
    //endregion
}
```

This architecture paradigm is:

![](https://raw.githubusercontent.com/tonivecina/android-clean-architecture/master/images/screen_activity_paradigm.png)

## Entities

The entities are localized in two categories, **Local** for memory resources and **Dynamic** for API resources (for example).

### Local entity

A local entity could be Credentials. Look at this example code:

```Android
final public class Credentials {
    
    @SuppressWarnings("FieldCanBeLocal")
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
    
    public boolean isLogged() {
        return getEmail() != null && getPasswordHash() != null;
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

### Dynamic entity

This example is for simple dynamic entity:

```Android
public class Profile {
    private String mName;
    private String mImageUrl;

    //region GettersReg

    public String getName() {
        return mName;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    //endregion

    //region SettersReg

    public void setName(String name) {
        mName = name;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    //endregion
}
```

## Patterns

The folder contains custom properties for primitives elements like booleans validators, formatters...

For example, If we want a validator of email format:

```Android
final public class Boolean {

    public static boolean isValidEmail(CharSequence email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
```

## Services

## Views

For a good design is necessary that we have custom views like EditText, TextViews, etc. This custom classes must be localized here.

This is a simple example for an EditText of login view:

```Android
public class LoginFormEditText extends AppCompatEditText {

    public LoginFormEditText(Context context) {
        super(context);
        view(context);
    }

    public LoginFormEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        view(context);
    }

    public LoginFormEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        view(context);
    }

    private void view(Context context) {
        // Custom properties here.
    }
}
```

## Recommendations

* Always to use region tags for Getters and Setters inside of class.

```android
//region GettersReg

...

//end

//region SettersReg

...

///end
```

* The classes must not contains more than 250 lines.
* Not use nomenclature for parameters or methods with more than 80 characters.

## Guidelines

* [Code Style for Contributors](https://source.android.com/source/code-style)
* [Ribot Project guidelines](https://github.com/ribot/android-guidelines/blob/master/project_and_code_guidelines.md)