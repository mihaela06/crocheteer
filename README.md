# Crocheteer

An Android app for crochet enthusiasts that want to better manage their yarn stash.

## How to build

Before building the project, you need to create a file containing your Ravelry API credentials used for authentication:
#### **`/app/src/main/res/values/secrets.xml`**
``` xml
<resources>
    <string name="ravelry_user">YOUR_RAVELRY_API_USERNAME</string>
    <string name="ravelry_password">YOUR_RAVELRY_API_PASSWORD</string>
</resources>
```

More information about how to obtain your credentials can be found visiting [the official group for Ravelry API developers](https://www.ravelry.com/groups/ravelry-api).