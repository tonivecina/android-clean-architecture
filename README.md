# Android clean architecture

This guide will show how to build a Android project with a clean architecture. It's very important we have a project with packages and subpackages ordered by services, implementations and android functionalities.

## Table of contents

* [Packages](#packages)
	* [Activities](#activities-package)
	* [Application](#application-package)
	* [Entities](#entities-package)
	* [Patterns](#patterns-package)
	* [Services](#services-package)
	* [Views](#views)

* [Activities and fragments](#activities-and-fragments)
	* [View](#view)
	* [Processor](#processor)
	* [Listeners](#listener)
	* [Routes](#routes)
	* [Interactor](#interactor)

* [Entities](#entities)
	* [Database](#database-entity)
	* [Dynamic](#dynamic-entity)
	* [Local](#local-entity)
	
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


### Application package

If you wish manage application class of your project, Configuration file is your file and it will be contained in this package. If your project contains a local database, create the clase here, for example: AppDataBase.

![](https://raw.githubusercontent.com/tonivecina/android-clean-architecture/master/images/screen_package_application.png)

### Entities package

All entities must be contained here and must be categorized like Database, Dynamic or Local.

* Dynamic for generic entities, for example entities for an Api.
* Database entities.
* Local for entities with memory storage, for example: Credentials.

![](https://raw.githubusercontent.com/tonivecina/android-clean-architecture/master/images/screen_package_entities.png)

### Patterns package

The global patterns that it can be used at any class are contained here like Boolean, String, etc. File of class must be object name with Pattern; StringPattern, BooleanPattern...

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

    static final int REQUEST_CODE = 1001;

    @SuppressWarnings("FieldCanBeLocal")
    private RecyclerView noteRecyclerView;

    @SuppressWarnings("FieldCanBeLocal")
    private AddButton addButton;

    private MainRecyclerViewNoteAdapter noteAdapter;

    @SuppressWarnings("FieldCanBeLocal")
    private MainProcessor mProcessor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        synchronized (this) {
            mProcessor = new MainProcessor(this);
            noteAdapter = new MainRecyclerViewNoteAdapter();
        }

        noteRecyclerView = (RecyclerView) findViewById(R.id.activity_main_recyclerView);
        noteRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        noteRecyclerView.setItemAnimator(new DefaultItemAnimator());
        noteRecyclerView.setAdapter(noteAdapter);

        View.OnClickListener onClickListener = mProcessor.getOnClickListener();

        addButton = (AddButton) findViewById(R.id.activity_main_button_add);
        addButton.setOnClickListener(onClickListener);

        mProcessor.onCreate();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            onNoteActivityResult(resultCode, data);
        }
    }

    //region Private methods
    private void onNoteActivityResult(int resultCode, Intent data) {

        if (resultCode != Activity.RESULT_OK) {
            DLog.info("Note creation was cancelled");
            return;
        }

        Note note = (Note) data.getSerializableExtra(AddNoteActivity.BUNDLE_NOTE);

        if (note != null) {
            addNote(note);
        }
    }
    //endregion

    //region Setters
    void setNotes(final List<Note> notes) {
        noteAdapter.setNotes(notes);
        noteAdapter.notifyDataSetChanged();
    }

    void addNote(final Note note) {
        noteAdapter.addNote(note);
        noteAdapter.notifyDataSetChanged();
    }
    //endregion
}
```

### Processor

The Process class will manage all elements in this package. This class is the communication channel with the view. Furthermore all elements in this package except the view will be initialized here.

This is a Process example with Listener and Interactor:

```Android
final class MainProcessor implements
        MainListeners.ActionListener,
        MainListeners.NotesListener
{
    @SuppressWarnings("FieldCanBeLocal")
    private MainActivity view;

    private MainRoutes routes;
    private MainOnClickListener onClickListener;
    private MainInteractorNotes notesInteractor;

    MainProcessor(MainActivity view) {
        this.view = view;
    }

    void onCreate() {
        getNotes();
    }

    //region Getters
    private synchronized MainInteractorNotes getNotesInteractor() {
        if (notesInteractor == null) {
            notesInteractor = new MainInteractorNotes(this);
        }

        return notesInteractor;
    }

    private synchronized MainRoutes getRoutes() {
        if (routes == null) {
            routes = new MainRoutes(view);
        }

        return routes;
    }

    synchronized MainOnClickListener getOnClickListener() {

        if (onClickListener == null) {
            onClickListener = new MainOnClickListener(this);
        }

        return onClickListener;
    }

    private void getNotes() {
        getNotesInteractor().getAll();
    }
    //endregion

    //region ActionListener
    @Override
    public void createNote() {
        getRoutes().intentAddNoteActivity();
    }
    //endregion

    //region NotesListener
    @Override
    public void onNotesReceived(List<Note> notes) {
        view.setNotes(notes);
    }
    //endregion
}
```

### Listeners

All communication between processor and interactors must be bidirectional through listeners. All listeners needs by interactors will be declared in Listener (abstract class).

```Android
abstract class MainListeners {

    interface ActionListener {
        void createNote();
    }

    interface NotesListener {
        void onNotesReceived(List<Note> notes);
    }
}
```

### Interactors

The Interactors can communicate directly with entities to manage data or services.

```Android
final class MainInteractorNotes {

    private AppDataBase dataBase;
    private MainListeners.NotesListener listener;

    MainInteractorNotes(MainListeners.NotesListener listener) {
        dataBase = Configuration
                .getInstance()
                .getAppDataBase();

        this.listener = listener;
    }

    //region Getters
    void getAll() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                List<Note> notes = dataBase.noteDao().getAll();
                Collections.reverse(notes);

                listener.onNotesReceived(notes);
            }
        });
    }
    //endregion
}
```

Implementations like OnClickListener be able to be considered as special interactor.

```Android
final class MainOnClickListener implements View.OnClickListener {

    private MainListeners.ActionListener listener;

    MainOnClickListener(MainListeners.ActionListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();

        if (id == R.id.activity_main_button_add) {
            listener.createNote();
        }
    }
}
```

In view must be declared:

```Android
View.OnClickListener onClickListener = mProcessor.getOnClickListener();

addButton = (AddButton) findViewById(R.id.activity_main_button_add);
addButton.setOnClickListener(onClickListener);
```

### Routes

All navigations and connections with other activites or fragments are defined here. You apply intents here. Look at this example:

```Android
final class MainRoutes {

    private MainActivity view;

    MainRoutes(MainActivity view) {
        this.view = view;
    }

    void intentAddNoteActivity() {
        Intent intent = new Intent(view, AddNoteActivity.class);
        view.startActivityForResult(intent, MainActivity.REQUEST_CODE, null);
    }
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

### Database entity

This example with Room service:

```Android
@Entity(tableName = "notes")
public final class Note implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "createdAt")
    private long createdAt;

    //region Getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    @Ignore
    public Date getCreatedDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(createdAt);

        return calendar.getTime();
    }
    //endregion

    //region Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
    //endregion
}
```

## Patterns

The folder contains custom properties for primitives elements like booleans validators, formatters...

For example, If we want a validator of email format:

```Android
public final class BooleanPattern {

    private BooleanPattern() {
        // empty constructor
    }

    public static boolean isValidEmail(CharSequence email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
```

## Services

If your project needs location services, network services... Please, not duplicate code. We create shared services for views that need it.

This example is a location service:

```Android
public class LocationService implements LocationListener {

    public interface LocationServiceListener {
        void onLocationUnavailable(String message);
        void onLocationSuccess(Location location);
    }

    private Activity mActivity;
    private LocationServiceListener mListener;
    private LocationManager mLocationManager;

    public LocationService(Activity activity, LocationServiceListener listener) {
        mActivity = activity;
        mListener = listener;
        mLocationManager = (LocationManager) mActivity.getSystemService(Context.LOCATION_SERVICE);
    }

    private boolean isGpsAvailable() {
        return mLocationManager.isProviderEnabled(GPS_PROVIDER);
    }

    private boolean isNetworkAvailable() {
        return mLocationManager.isProviderEnabled(NETWORK_PROVIDER);
    }

    public void getLocation() {
        if (!isGpsAvailable() && !isNetworkAvailable()) {
            String message = mActivity.getString(R.string.ERROR_localization_unavailable_lbl);
            mListener.onLocationUnavailable(message);
            return;
        }

        updateLocation();
    }

    private void updateLocation() {
        boolean internetConnection = ConnectivityService.isNetworkAvailable();

        if (!internetConnection) {
            String message = mActivity.getString(R.string.ERROR_internet_connection_unavailable_lbl);
            mListener.onLocationUnavailable(message);
            DebugLog.error("Internet connection unavailable");

            return;
        }

        int finePermission = ContextCompat
                .checkSelfPermission(
                        mActivity,
                        Manifest.permission.ACCESS_FINE_LOCATION);

        int coarsePermission = ContextCompat
                .checkSelfPermission(
                        mActivity,
                        Manifest.permission.ACCESS_COARSE_LOCATION);

        if (finePermission != PackageManager.PERMISSION_GRANTED
                && coarsePermission != PackageManager.PERMISSION_GRANTED)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                String[] permissions = new String[] {
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                };

                ActivityCompat.requestPermissions(mActivity, permissions, 200);
            }
        }
        else {
            String provider = NETWORK_PROVIDER;

            if (!isNetworkAvailable()) {
                provider = GPS_PROVIDER;
                Log.d("LocationService", "Gps provider unavailable");
                Log.d("LocationService", "Use network provider");
            }

            mLocationManager.requestLocationUpdates(provider, 0, 0, this);
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        String message = mActivity.getString(R.string.ERROR_localization_unavailable_lbl);
        mListener.onLocationUnavailable(message);
    }

    @Override
    public void onProviderEnabled(String provider) {
        updateLocation();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        switch (status) {
            case LocationProvider.OUT_OF_SERVICE:
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                String message = mActivity.getString(R.string.ERROR_localization_unavailable_lbl);
                mListener.onLocationUnavailable(message);
                break;
            case LocationProvider.AVAILABLE:
                updateLocation();
                break;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        int finePermission = ContextCompat
                .checkSelfPermission(
                        mActivity,
                        Manifest.permission.ACCESS_FINE_LOCATION);

        int coarsePermission = ContextCompat
                .checkSelfPermission(
                        mActivity,
                        Manifest.permission.ACCESS_COARSE_LOCATION);

        if (finePermission == PackageManager.PERMISSION_GRANTED
                && coarsePermission == PackageManager.PERMISSION_GRANTED)
        {
            mLocationManager.removeUpdates(this);
            mListener.onLocationSuccess(location);
        }
    }
}
```

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

## References

* [Code Style for Contributors](https://source.android.com/source/code-style)
* [Ribot Project guidelines](https://github.com/ribot/android-guidelines/blob/master/project_and_code_guidelines.md)