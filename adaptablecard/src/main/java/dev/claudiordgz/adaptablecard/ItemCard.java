package dev.claudiordgz.adaptablecard;

import android.content.Context;
import android.content.res.TypedArray;
import android.media.Image;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lb.auto_fit_textview.AutoResizeTextView;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import dev.claudiordgz.common.model.DimensionConverter;
import dev.claudiordgz.common.model.InstagramData;

/**
 * Created by Claudio on 3/31/2015.
 */
public class ItemCard extends FrameLayout {

  private CardView mCardViewItem;
  private ImageView mImageViewImage;
  private ImageView mImageViewUserProfile;
  private AutoResizeTextView mTextViewUsername;
  private AutoResizeTextView mTextViewLikeCount;
  private ImageView mImageViewThumbsUp;

  private String mImageUrl;
  private String mUserProfileUrl;
  private String mUserName;
  private String mLikeCount;
  private int mWidth = 0;
  private int mHeight = 0;
  private int userProfilePictureSize = 0;
  private int userPictureSize = 0;
  private LayoutInflater mInflater;

  private void setCardWithDp(int dpWidth) {
    mWidth = setCardSizeDp(dpWidth);
  }

  private void setCardHeight(int dpHeight) {
    mHeight = setCardSizeDp(dpHeight);
  }

  private int setCardSizeDp(int dpValue) {
    return (int) DimensionConverter.convertDpToPixel(dpValue, getContext());
  }

  public ItemCard(Context context, AttributeSet attrs) {
    super(context, attrs);
    mInflater = LayoutInflater.from(context);
    init();
    initViews(context, attrs);
  }

  public ItemCard(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    mInflater = LayoutInflater.from(context);
    init();
    initViews(context, attrs);
  }

  public void init() {
    View v = mInflater.inflate(R.layout.instagram_item, this, true);
    mCardViewItem = (CardView) v.findViewById(R.id.instagram_card_view);
    mImageViewImage = (ImageView) v.findViewById(R.id.image_url);
    mImageViewUserProfile = (ImageView) v.findViewById(R.id.user_profile_url);
    mTextViewUsername = (AutoResizeTextView) v.findViewById(R.id.username);
    mTextViewLikeCount = (AutoResizeTextView) v.findViewById(R.id.count);
    mImageViewThumbsUp = (ImageView) v.findViewById(R.id.cv_thumbgs_up);
    ButterKnife.inject(this, v);
  }

  private void initViews(Context context, AttributeSet attrs) {
    if (attrs == null) return;
    TypedArray layout = context.getTheme().obtainStyledAttributes(attrs,
        R.styleable.InatagramCardView, 0, 0);
    try {
      this.mImageUrl = layout.getString(R.styleable.InatagramCardView_image_url);
      this.mUserProfileUrl = layout.getString(R.styleable.InatagramCardView_username_profile_url);
      this.mUserName = layout.getString(R.styleable.InatagramCardView_username);
      this.mLikeCount = layout.getString(R.styleable.InatagramCardView_likes);
      this.mWidth = (int) layout.getDimension(R.styleable.InatagramCardView_card_width, mWidth);
      this.mHeight = (int) layout.getDimension(R.styleable.InatagramCardView_card_height, mHeight);
    } finally {
      layout.recycle();
    }
    setAllVariables(context);
  }

  private void setAllVariables(Context context) {
    setUserPictureSize();
    setUserProfilePictureSize();
    pushImages();
    pushText();
  }

  public void SetFromInstagramData(InstagramData data, int width, int height) {
    mWidth = width;
    mHeight = height;
    setImageUrl(data.imageUrl);
    setUserProfileUrl(data.usernameProfileImage);
    setUserName(data.username);
    setLikeCount(data.likesCount);
    setUserPictureSize();
    setUserProfilePictureSize();
    pushImages();
    pushText();
  }

  private void pushText() {
    mTextViewLikeCount.setMaxLines(1);
    int userNameWidth = (int) (mWidth * 0.45);
    int likeCountWidth = (int) (mWidth * 0.1);
    mTextViewLikeCount.setPadding(20, 0, 0, 0);
    mTextViewLikeCount.getLayoutParams().width = likeCountWidth;
    mTextViewLikeCount.setText(mLikeCount);
    mTextViewUsername.getLayoutParams().width = userNameWidth;
    mTextViewUsername.setMaxLines(1);
    mTextViewUsername.setText(mUserName);
  }

  private void pushImages() {

    mImageViewImage.getLayoutParams().height = userPictureSize;
    mImageViewImage.getLayoutParams().width = userPictureSize;
    mImageViewUserProfile.getLayoutParams().height = userProfilePictureSize;
    mImageViewUserProfile.getLayoutParams().width = userProfilePictureSize;
    mCardViewItem.getLayoutParams().width = mWidth;
    mCardViewItem.getLayoutParams().height = mHeight;
    double thumbsUpSize = mWidth * (0.09);
    mImageViewThumbsUp.getLayoutParams().width = (int) thumbsUpSize;
    mImageViewThumbsUp.getLayoutParams().height = (int) thumbsUpSize;
    Picasso.with(getContext())
        .load(mImageUrl)
        .fit().centerCrop()
        .into(mImageViewImage);
    Picasso.with(getContext())
        .load(mUserProfileUrl)
        .into(mImageViewUserProfile);
  }

  public void setImageUrl(String mImageUrl) {
    this.mImageUrl = mImageUrl;

  }

  public void setUserProfileUrl(String mUserProfileUrl) {
    this.mUserProfileUrl = mUserProfileUrl;
  }

  public void setUserName(String mUserName) {
    this.mUserName = mUserName;
  }

  public void setLikeCount(String mLikeCount) {
    this.mLikeCount = mLikeCount;
  }

  public void setWidthAndHeight(int width, int height) {
    mWidth = (int) DimensionConverter.convertDpToPixel(width, getContext());
    mHeight = (int) DimensionConverter.convertDpToPixel(height, getContext());
    setUserProfilePictureSize();
    setUserPictureSize();
  }

  public void setUserProfilePictureSize() {
    this.userProfilePictureSize = (int) (mHeight / 5 - DimensionConverter.convertDpToPixel(16, getContext()));
  }

  public void setUserPictureSize() {
    this.userPictureSize = (int) (((mHeight / 5) * 4) - DimensionConverter.convertDpToPixel(16, getContext()));
  }
}