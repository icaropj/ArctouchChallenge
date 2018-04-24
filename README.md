# ArctouchChallenge
Project challenge

# Third-party libraries

### Retrofit

Library to make API requests easy. Used to fetch list of movies and movie details.

### Converter-Gson

Library used to convert Json to Java Objects and Java Objects to Json.

### Butterknife

Library used for View Injection, so I don't need to call findViewByid(), setup OnClickListeners and other view events.

### EventBus

Library publisher/subscriber, used so I don't need to setup interface listeners.

### Dagger

Library for DI(Dependency Injection). Used so I don't have to worry about the objects construction, just inject them on the view.

### Picasso

Library to easily load image resources. Used to load movie posters.

### Parceler

Libraty to make objects parcelable easily. Used to pass parcelable objects through intent extras and to gain performance over Serializable interface.
