package dev.claudiordgz.common.model;


/**
 * Created by Claudio on 3/31/2015.
 */
public class InstagramData {
    private String TAG = getClass().getName();

    public String imageUrl;
    public String username;
    public String usernameProfileImage;
    public String likesCount;

    private int hashId = 0;

    public int hashCode() {
        if(hashId == 0){
            String idStr = imageUrl.substring(imageUrl.lastIndexOf('/') + 1);
            idStr = idStr.substring(0, idStr.lastIndexOf('.'));
            idStr = idStr.replace("_", "");
            idStr = idStr.replace("<", "");
            idStr = idStr.replaceAll("[^\\d.]", "");
            String specChar = "" +idStr.charAt(idStr.length()-10);
            int idx = Integer.parseInt(specChar) == 1 ? 10 : 9;
            idStr = idStr.substring(idStr.length()-idx);
            hashId = Integer.parseInt(idStr);
        }
        return hashId;
    }
    public boolean equals(Object o) {
        if (o instanceof InstagramData) {
            InstagramData other = (InstagramData) o;
            return (imageUrl.equals(other.imageUrl));
        }
        return false;
    }
}
