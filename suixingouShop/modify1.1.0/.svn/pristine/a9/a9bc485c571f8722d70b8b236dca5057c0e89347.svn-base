package com.yto.zhang.store.util.adapters;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class MyExpandableListAdapter extends BaseExpandableListAdapter {
	private ViewBinder mViewBinder;

	private List<? extends Map<String, ?>> mGroupData;
	private int mExpandedGroupLayout;
	private int mCollapsedGroupLayout;
	private String[] mGroupFrom;
	private int[] mGroupTo;

	private List<? extends List<? extends Map<String, ?>>> mChildData;
	private int mChildLayout;
	private int mLastChildLayout;
	private String[] mChildFrom;
	private int[] mChildTo;

	private LayoutInflater mInflater;

	public MyExpandableListAdapter(Context context, List<? extends Map<String, ?>> groupData,
			int groupLayout, String[] groupFrom, int[] groupTo,
			List<? extends List<? extends Map<String, ?>>> childData, int childLayout,
			String[] childFrom, int[] childTo) {
		this(context, groupData, groupLayout, groupLayout, groupFrom, groupTo, childData,
				childLayout, childLayout, childFrom, childTo);
	}

	public MyExpandableListAdapter(Context context, List<? extends Map<String, ?>> groupData,
			int expandedGroupLayout, int collapsedGroupLayout, String[] groupFrom, int[] groupTo,
			List<? extends List<? extends Map<String, ?>>> childData, int childLayout,
			String[] childFrom, int[] childTo) {
		this(context, groupData, expandedGroupLayout, collapsedGroupLayout, groupFrom, groupTo,
				childData, childLayout, childLayout, childFrom, childTo);
	}

	public MyExpandableListAdapter(Context context, List<? extends Map<String, ?>> groupData,
			int expandedGroupLayout, int collapsedGroupLayout, String[] groupFrom, int[] groupTo,
			List<? extends List<? extends Map<String, ?>>> childData, int childLayout,
			int lastChildLayout, String[] childFrom, int[] childTo) {
		mGroupData = groupData;
		mExpandedGroupLayout = expandedGroupLayout;
		mCollapsedGroupLayout = collapsedGroupLayout;
		mGroupFrom = groupFrom;
		mGroupTo = groupTo;

		mChildData = childData;
		mChildLayout = childLayout;
		mLastChildLayout = lastChildLayout;
		mChildFrom = childFrom;
		mChildTo = childTo;

		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public Object getChild(int groupPosition, int childPosition) {
		return mChildData.get(groupPosition).get(childPosition);
	}

	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	/**
	 * Instantiates a new View for a child.
	 * @param isLastChild Whether the child is the last child within its group.
	 * @param parent The eventual parent of this new View.
	 * @return A new child View
	 */
	public View newChildView(boolean isLastChild, ViewGroup parent) {
		return mInflater.inflate((isLastChild) ? mLastChildLayout : mChildLayout, parent, false);
	}

	public int getChildrenCount(int groupPosition) {
		return mChildData.get(groupPosition).size();
	}

	public Object getGroup(int groupPosition) {
		return mGroupData.get(groupPosition);
	}

	public int getGroupCount() {
		return mGroupData.size();
	}

	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	/**
	 * Instantiates a new View for a group.
	 * @param isExpanded Whether the group is currently expanded.
	 * @param parent The eventual parent of this new View.
	 * @return A new group View
	 */
	public View newGroupView(boolean isExpanded, ViewGroup parent) {
		return mInflater.inflate((isExpanded) ? mExpandedGroupLayout : mCollapsedGroupLayout,
				parent, false);
	}

	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	public boolean hasStableIds() {
		return true;
	}

	// --------------------------------------------------------------------------------------//

	private void bindView(View view, Map<String, ?> data, String[] from, int[] to) {
		int len = to.length;

		boolean isBound = false;
		for (int i = 0; i < len; i++) {

			final View v = view.findViewById(to[i]);

			if (v!=null) {
                final Object _data = data.get(from[i]);
                String text = _data == null ? "" : data.toString();
                if (text == null) {
                    text = "";
                }
				
				if (mViewBinder != null) {//���Binder��Ϊ��,ʹ��Binder���д���
					isBound = mViewBinder.setViewValue(v, data.get(from[i]), text);
				}
				
				if (!isBound) {//���Binder���,ʹ��ԭ���ķ������д���
					TextView _v = (TextView)v;
					_v.setText((String)data.get(from[i]));
				}				
			}
		}
	}

	public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
			ViewGroup parent) {
		View v;
		if (convertView == null) {
			v = newGroupView(isExpanded, parent);
		} else {
			v = convertView;
		}

		bindView(v, mGroupData.get(groupPosition), mGroupFrom, mGroupTo);
		return v;
	}

	public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
			View convertView, ViewGroup parent) {
		View v;
		if (convertView == null) {
			v = newChildView(isLastChild, parent);
		} else {
			v = convertView;
		}

		bindView(v, mChildData.get(groupPosition).get(childPosition), mChildFrom, mChildTo);
		return v;
	}

	public void setViewBinder(ViewBinder mViewBinder) {
		this.mViewBinder = mViewBinder;
	}

	/**
	 * �ṩ��ͼ��Ⱦ�İ��� 
	 * @author Atomic
	 */
	public static interface ViewBinder {
		boolean setViewValue(View view, Object data, String textRepresentation);
	}
	

	// private View createViewFromResource(int position, View convertView,
	// ViewGroup parent, int resource) {
	// View v;
	// if (convertView == null) {
	// v = mInflater.inflate(resource, parent, false);
	// } else {
	// v = convertView;
	// }
	//
	// bindView(position, v);
	//
	// return v;
	// }

	// private void bindView(int position, View view) {
	// final Map dataSet = mData.get(position);
	// if (dataSet == null) {
	// return;
	// }
	//
	// final ViewBinder binder = mViewBinder;
	// final String[] from = mFrom;
	// final int[] to = mTo;
	// final int count = to.length;
	//
	// for (int i = 0; i < count; i++) {
	// final View v = view.findViewById(to[i]);
	// if (v != null) {
	// final Object data = dataSet.get(from[i]);
	// String text = data == null ? "" : data.toString();
	// if (text == null) {
	// text = "";
	// }
	//
	// boolean bound = false;
	// if (binder != null) {
	// bound = binder.setViewValue(v, data, text);
	// }
	//
	// if (!bound) {
	// if (v instanceof Checkable) {
	// if (data instanceof Boolean) {
	// ((Checkable) v).setChecked((Boolean) data);
	// } else if (v instanceof TextView) {
	// // Note: keep the instanceof TextView check at the bottom of these
	// // ifs since a lot of views are TextViews (e.g. CheckBoxes).
	// setViewText((TextView) v, text);
	// } else {
	// throw new IllegalStateException(v.getClass().getName() +
	// " should be bound to a Boolean, not a " +
	// (data == null ? "<unknown type>" : data.getClass()));
	// }
	// } else if (v instanceof TextView) {
	// // Note: keep the instanceof TextView check at the bottom of these
	// // ifs since a lot of views are TextViews (e.g. CheckBoxes).
	// setViewText((TextView) v, text);
	// } else if (v instanceof ImageView) {
	// if (data instanceof Integer) {
	// setViewImage((ImageView) v, (Integer) data);
	// } else {
	// setViewImage((ImageView) v, text);
	// }
	// } else {
	// throw new IllegalStateException(v.getClass().getName() + " is not a " +
	// " view that can be bounds by this SimpleAdapter");
	// }
	// }
	// }
	// }
	// }
}
