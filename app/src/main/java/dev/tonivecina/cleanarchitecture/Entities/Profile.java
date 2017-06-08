package dev.tonivecina.cleanarchitecture.Entities;

/**
 * @author Toni Vecina on 6/8/17.
 */

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
