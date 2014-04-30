/*
 * The MIT License
 *
 * Copyright 2012 Masahiko, SAWAI <masahiko.sawai@gmail.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.sdamsdam.widget;

import android.annotation.*;
import android.content.*;
import android.os.*;
import android.util.*;
import android.widget.*;

/**
 *
 * @author Masahiko, SAWAI <masahiko.sawai@gmail.com>
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class GridViewAppWidgetRemoteViewsService extends RemoteViewsService
{

	private static final String LOG_TAG = "XXX";

	@Override
	public RemoteViewsFactory onGetViewFactory(Intent intent)
	{
		GridViewAppWidgetRemoteViewsFactory factory = new GridViewAppWidgetRemoteViewsFactory();
		return factory;
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	class GridViewAppWidgetRemoteViewsFactory implements RemoteViewsFactory
	{

		private String[] shouts;

		public void onCreate()
		{
			Log.v(LOG_TAG, "RemoteViewsFactory#onCreate() : Hello");
			shouts = getApplicationContext().getResources().getStringArray(R.array.shouts);
			Log.v(LOG_TAG, "RemoteViewsFactory#onCreate() : Bye");
		}

		public void onDataSetChanged()
		{
			Log.v(LOG_TAG, "RemoteViewsFactory#onDataSetChanged() : Hello");
			Log.v(LOG_TAG, "RemoteViewsFactory#onDataSetChanged() : Bye");
		}

		public void onDestroy()
		{
			Log.v(LOG_TAG, "RemoteViewsFactory#onDestroy() : Hello");
			Log.v(LOG_TAG, "RemoteViewsFactory#onDestroy() : Bye");
		}

		public int getCount()
		{
			int count = 0;
			Log.v(LOG_TAG, "RemoteViewsFactory#getCount() : Hello");

			if (shouts != null)
			{
				count = shouts.length;
			}
			Log.d(LOG_TAG, "RemoteViewsFactory#getCount() : count => " + count);

			Log.v(LOG_TAG, "RemoteViewsFactory#getCount() : Bye");
			return count;
		}

		@SuppressLint("NewApi")
		public RemoteViews getViewAt(int position)
		{
			Log.v(LOG_TAG, "RemoteViewsFactory#getViewAt() : Hello");
			Log.d(LOG_TAG, "RemoteViewsFactory#getViewAt() : position => " + position);

			RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(),
				R.layout.gridview_item);

			
			switch(position)
			{
			case 0:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_02);				break;				
			case 1:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_03);				break;
			case 2:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_04);				break;
			case 3:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_05);				break;
			case 4:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_06);				break;				
			case 5:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_07);				break;
			case 6:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_08);				break;
			case 7:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_09);				break;
			case 8:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_10);				break;				
			case 9:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_11);				break;
			case 10:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_12);				break;
			case 11:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_13);				break;
			case 12:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_14);				break;				
			case 13:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_15);				break;
			case 14:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_16);				break;
			case 15:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_17);				break;
			case 16:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_18);				break;				
			case 17:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_19);				break;
			case 18:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_20);				break;
			case 19:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_21);				break;
			case 20:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_22);				break;				
			case 21:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_23);				break;
			case 22:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_24);				break;
			case 23:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_25);				break;
			case 24:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_26);				break;				
			case 25:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_27);				break;
			case 26:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_28);				break;
			case 27:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_29);				break;
			case 28:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_30);				break;				
			case 29:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_31);				break;
			case 30:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_32);				break;
			case 31:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_33);				break;
			case 32:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_34);				break;		

			case 33:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_35);				break;
			case 34:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_36);				break;
			case 35:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_37);				break;				
			case 36:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_38);				break;
			case 37:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_39);				break;
			case 38:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_40);				break;
			case 39:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_41);				break;				
			case 40:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_42);				break;
			case 41:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_43);				break;
			case 42:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_44);				break;
			case 43:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_45);				break;				
			case 44:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_46);				break;
			case 45:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_47);				break;
			case 46:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_48);				break;
			case 47:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_49);				break;				
			case 48:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_50);				break;
			case 49:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_51);				break;
			case 50:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_52);				break;
			case 51:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_53);				break;				
			case 52:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_54);				break;
			case 53:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_55);				break;
			case 54:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_56);				break;
			case 55:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_57);				break;	
			
			
			case 56:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_58);				break;
			case 57:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_59);				break;
			case 58:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_60);				break;				
			case 59:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_61);				break;
			case 60:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_62);				break;
			case 61:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_63);				break;
			case 62:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_64);				break;				
			case 63:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_65);				break;
			case 64:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_66);				break;
			case 65:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_67);				break;
			case 66:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_68);				break;				
			case 67:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_69);				break;
			case 68:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_70);				break;
			case 69:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_71);				break;
			case 70:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_72);				break;				
			case 71:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_73);				break;
			case 72:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_74);				break;
			case 73:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_75);				break;
			case 74:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_76);				break;				
			case 75:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_77);				break;
			case 76:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_78);				break;
			case 77:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_79);				break;
			case 78:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_80);				break;	
			
			
			case 79:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_81);				break;
			case 80:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_82);				break;
			case 81:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_83);				break;				
			case 82:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_84);				break;
			case 83:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_85);				break;
			case 84:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_86);				break;
			case 85:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_87);				break;				
			case 86:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_88);				break;
			case 87:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_89);				break;
			case 88:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_90);				break;
			case 89:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_91);				break;				
			case 90:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_92);				break;
			case 91:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_93);				break;
			case 92:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_94);				break;
			case 93:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_95);				break;				
			case 94:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_96);				break;
			case 95:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_97);				break;
			case 96:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_98);				break;
			case 97:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_99);				break;				
			case 98:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_100);				break;
			case 99:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_101);				break;
			case 100:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_102);				break;
			case 101:			    remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_103);				break;	
			
			
			case 102:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_104);				break;
			case 103:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_105);				break;
			case 104:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_106);				break;				
			case 105:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_107);				break;
			case 106:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_108);				break;
			case 107:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_109);				break;
			case 108:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_110);				break;				
			case 109:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_111);				break;
			case 110:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_112);				break;
			case 111:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_113);				break;
			case 112:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_114);				break;				
			case 113:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_115);				break;
			case 114:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_116);				break;
			case 115:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_117);				break;
			case 116:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_118);				break;				
			case 117:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_119);				break;
			case 118:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_120);				break;
			case 119:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_121);				break;
			case 120:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_122);				break;				
			case 121:				remoteViews.setImageViewResource(R.id.eggparts, R.drawable.egg_123);				break;

		
			}
			
			

			// remoteViews がクリックされた際に発行されるインテントは
			// setPendingIntentTemplate() で設定したインテントに以下のインテントの
			// 情報を追加( Intent#fillIn() )したものとなる
			Intent fillInIntent = new Intent();
			fillInIntent.putExtra("EXTRA_NAME", shouts[position]);
			remoteViews.setOnClickFillInIntent(R.id.eggparts, fillInIntent);

			Log.v(LOG_TAG, "RemoteViewsFactory#getViewAt() : Bye");
			return remoteViews;
		}

		public RemoteViews getLoadingView()
		{
			Log.v(LOG_TAG, "RemoteViewsFactory#getLoadingView() : Hello");
			Log.v(LOG_TAG, "RemoteViewsFactory#getLoadingView() : Bye");
			return null;
		}

		public int getViewTypeCount()
		{
			Log.v(LOG_TAG, "RemoteViewsFactory#getViewTypeCount() : Hello");
			Log.v(LOG_TAG, "RemoteViewsFactory#getViewTypeCount() : Bye");
			return 1;
		}

		public long getItemId(int position)
		{
			Log.v(LOG_TAG, "RemoteViewsFactory#getItemId() : Hello");
			Log.d(LOG_TAG, "RemoteViewsFactory#getItemId() : position => " + position);
			Log.v(LOG_TAG, "RemoteViewsFactory#getItemId() : Bye");
			return position;
		}

		public boolean hasStableIds()
		{
			Log.v(LOG_TAG, "RemoteViewsFactory#hasStableIds() : Hello");
			Log.v(LOG_TAG, "RemoteViewsFactory#hasStableIds() : Bye");
			return true;
		}
	}

	public static int getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
}
