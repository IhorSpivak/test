package llc.net.mydutyfree.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import llc.net.mydutyfree.base.MDFApplication;

public class FontUtils {
	static interface FontTypes {
		public static String REGULAR = "Regular";
		public static String BOLD = "Bold";
		public static String ITALIC = "Italic";
        public static String BOLDITALIC = "BoldItalic";
	}

	static Map sFontPathMap = new HashMap();

	static {
		sFontPathMap.put(FontTypes.REGULAR, "fonts/SF-UI-Display-Regular.otf");
		sFontPathMap.put(FontTypes.BOLD, "fonts/SF-UI-Display-Bold.otf");
        sFontPathMap.put(FontTypes.ITALIC, "fonts/GOTHICI.TTF");
        sFontPathMap.put(FontTypes.BOLDITALIC, "fonts/GOTHICBI.TTF");
	}

	static Map sTypefaceCache = new HashMap();

	static Typeface getAppTypeface(Context context, String fontType) {
		String fontPath = (String) sFontPathMap.get(fontType);
		if (!sTypefaceCache.containsKey(fontType)) {
			sTypefaceCache.put(fontType, Typeface.createFromAsset(context.getAssets(), fontPath));
		}
		return (Typeface) sTypefaceCache.get(fontType);
	}

	static Typeface getAppTypeface(Context context, Typeface originalTypeface) {
		String appFontType = FontTypes.REGULAR;

		if (originalTypeface != null) {
			int style = originalTypeface.getStyle();
			switch (style) {
				case Typeface.BOLD:
					appFontType = FontTypes.BOLD;
					break;
                case Typeface.ITALIC:
                    appFontType = FontTypes.ITALIC;
                    break;
                case Typeface.BOLD_ITALIC:
                    appFontType = FontTypes.BOLDITALIC;
                    break;
                case Typeface.NORMAL:
                    appFontType = FontTypes.REGULAR;
                    break;
				default:
					appFontType = FontTypes.REGULAR;
			}
		}
		return getAppTypeface(context, appFontType);
	}

    public static void setAppFont(Context context, View view) {
        if (view instanceof ViewGroup) {
            int childCount = ((ViewGroup) view).getChildCount();
            for (int i = 0; i < childCount; i++)
                setAppFont(context, ((ViewGroup) view).getChildAt(i));
        } else if (view instanceof TextView) {
            Typeface currentTypeface = ((TextView) view).getTypeface();
            ((TextView) view).setTypeface(getAppTypeface(context, currentTypeface));
        }
    }

    public static void setNormalFont(Context context, View view) {
        if (view instanceof ViewGroup) {
            int childCount = ((ViewGroup) view).getChildCount();
            for (int i = 0; i < childCount; i++)
                setNormalFont(context, ((ViewGroup) view).getChildAt(i));
        } else if (view instanceof TextView) {
            ((TextView) view).setTypeface(MDFApplication.getNormalTypeface());
        } else if (view instanceof EditText) {
            ((EditText) view).setTypeface(MDFApplication.getNormalTypeface());
        }
        else if (view instanceof Button) {
            ((Button) view).setTypeface(MDFApplication.getNormalTypeface());
        }
        else if (view instanceof SlashStrikedTextView) {
            ((SlashStrikedTextView) view).setTypeface(MDFApplication.getNormalTypeface());
        }
    }

    public static void setNormalFont(Context context, View view, int textSizeInDp) {
        if (view instanceof ViewGroup) {
            int childCount = ((ViewGroup) view).getChildCount();
            for (int i = 0; i < childCount; i++)
                setBoldFont(context, ((ViewGroup) view).getChildAt(i));
        } else if (view instanceof TextView) {
            ((TextView) view).setTypeface(MDFApplication.getNormalTypeface());
            ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSizeInDp);
        }
        else if (view instanceof Button) {
            ((Button) view).setTypeface(MDFApplication.getNormalTypeface());
            ((Button) view).setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSizeInDp);
        }
    }


    public static void setBoldFont(Context context, View view) {
        if (view instanceof ViewGroup) {
            int childCount = ((ViewGroup) view).getChildCount();
            for (int i = 0; i < childCount; i++)
                setBoldFont(context, ((ViewGroup) view).getChildAt(i));
        } else if (view instanceof TextView) {
            ((TextView) view).setTypeface(MDFApplication.getBoldTypeface());
        }
        else if (view instanceof Button) {
            ((Button) view).setTypeface(MDFApplication.getBoldTypeface());
        }
        else if (view instanceof SlashStrikedTextView) {
            ((SlashStrikedTextView) view).setTypeface(MDFApplication.getBoldTypeface());
        }
    }

    public static void setBoldFont(Context context, View view, int textSizeInDp) {
        if (view instanceof ViewGroup) {
            int childCount = ((ViewGroup) view).getChildCount();
            for (int i = 0; i < childCount; i++)
                setBoldFont(context, ((ViewGroup) view).getChildAt(i));
        } else if (view instanceof TextView) {
            ((TextView) view).setTypeface(MDFApplication.getBoldTypeface());
            ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSizeInDp);
        }
        else if (view instanceof Button) {
            ((Button) view).setTypeface(MDFApplication.getBoldTypeface());
            ((Button) view).setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSizeInDp);
        }
    }

    public static void setItalicFont(Context context, View view) {
        if (view instanceof ViewGroup) {
            int childCount = ((ViewGroup) view).getChildCount();
            for (int i = 0; i < childCount; i++)
                setItalicFont(context, ((ViewGroup) view).getChildAt(i));
        } else if (view instanceof TextView) {
            ((TextView) view).setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/GOTHICI.TTF"));
        }
        else if (view instanceof Button) {
            ((Button) view).setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/GOTHICI.TTF"));
        }
        else if (view instanceof SlashStrikedTextView) {
            ((SlashStrikedTextView) view).setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/GOTHICI.TTF"));
        }

    }

    public static void setBoldItalicFont(Context context, View view) {
        if (view instanceof ViewGroup) {
            int childCount = ((ViewGroup) view).getChildCount();
            for (int i = 0; i < childCount; i++)
                setBoldItalicFont(context, ((ViewGroup) view).getChildAt(i));
        } else if (view instanceof TextView) {
            ((TextView) view).setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/GOTHICBI.TTF"));
        }
        else if (view instanceof Button) {
            ((Button) view).setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/GOTHICBI.TTF"));
        }
        else if (view instanceof SlashStrikedTextView) {
            ((SlashStrikedTextView) view).setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/GOTHICBI.TTF"));
        }
    }
}