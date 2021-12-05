package de.marcelpociot.circularslider;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.kroll.common.Log;
import org.appcelerator.titanium.proxy.TiViewProxy;
import org.appcelerator.titanium.view.TiUIView;
import org.appcelerator.titanium.util.TiConvert;
import android.app.Activity;
import java.util.HashMap;
import de.marcelpociot.circularslider.CircularSeekBar.OnSeekChangeListener;



@Kroll.proxy(creatableInModule = TiCircularSliderModule.class)
public class ViewProxy extends TiViewProxy {
	private static final String LCAT = "TiCircularSliderModule";
	// Get the screen's density scale

	CircularSeekBar csb;

	public ViewProxy() {
		super();

		Log.d(LCAT, "[VIEWPROXY LIFECYCLE EVENT] init");
	}

	@Override
	public TiUIView createView(Activity activity) {
		// This method is called when the view needs to be created. This is
		// a required method for a TiViewProxy subclass.
		TiUIView view = new View(this);
		view.getLayoutParams().autoFillsHeight = true;
		view.getLayoutParams().autoFillsWidth = true;
		return view;
	}

	// Handle creation options
	@Override
	public void handleCreationDict(KrollDict options) {
		// This method is called from handleCreationArgs if there is at least
		// argument specified for the proxy creation call and the first argument
		// is a KrollDict object.

		Log.d(LCAT, "VIEWPROXY LIFECYCLE EVENT] handleCreationDict " + options);

		// Calling the superclass method ensures that the properties specified
		// in the dictionary are properly set on the proxy object.
		super.handleCreationDict(options);
	}

	public void handleCreationArgs(KrollModule createdInModule, Object[] args) {
		// This method is one of the initializers for the proxy class. The
		// arguments
		// for the create call are passed as an array of objects. If your proxy
		// simply needs to handle a single KrollDict argument, use
		// handleCreationDict.
		// The superclass method calls the handleCreationDict if the first
		// argument
		// to the create method is a dictionary object.

		Log.d(LCAT, "VIEWPROXY LIFECYCLE EVENT] handleCreationArgs ");

		for (int i = 0; i < args.length; i++) {
			Log.d(LCAT, "VIEWPROXY LIFECYCLE EVENT] args[" + i + "] " + args[i]);
		}

		super.handleCreationArgs(createdInModule, args);
	}




	public class View extends TiUIView {
		// Standard Debugging variables
		private static final String LCAT = "TiCircularSliderModule";

		private static final String PROPERTY_VALUE = "value";
		private static final String PROPERTY_MAX_VALUE = "maximumValue";
		private static final String PROPERTY_LINE_WIDTH = "lineWidth";
		private static final String PROPERTY_COLOR_FILLED = "filledColor";
		private static final String PROPERTY_COLOR_UNFILLED = "unfilledColor";

		public View(TiViewProxy proxy) {
			super(proxy);

			Log.d(LCAT, "[VIEW LIFECYCLE EVENT] view");

			csb = new CircularSeekBar(proxy.getActivity());

			setNativeView(csb);

			csb.setSeekBarChangeListener(new OnSeekChangeListener() {

				@Override
				public void onProgressChange(CircularSeekBar view, int newProgress) {
					Log.d(LCAT,
							"Progress:" + csb.getProgress() + "/"
									+ csb.getMaxProgress());
					notifyOfChange(csb.getProgress());
				}
			});


		}

		// The view is automatically registered as a model listener when the view
		// is realized by the view proxy. That means that the processProperties
		// method will be called during creation and that propertiesChanged and
		// propertyChanged will be called when properties are changed on the proxy.

		@Override
		public void processProperties(KrollDict props) {
			super.processProperties(props);



			if (props.containsKey(PROPERTY_MAX_VALUE)) {
				int max = TiConvert.toInt(props.getInt(PROPERTY_MAX_VALUE));
				//if (max >= csb.getMinProgress()) {
					csb.setMaxProgress(max);
				// } else {
				// 	Log.e(LCAT, "Maximum value must be greater than minimum value.");
				// 	if (csb.getMaxProgress() < csb.getMinProgress()) {
				// 		csb.setMaxProgress(csb.getMinProgress() + 1);
				// 	}
				// }
			}

			if (props.containsKey(PROPERTY_VALUE)) {
				csb.setProgress(TiConvert.toInt(props.get(PROPERTY_VALUE)));
			}

			if (props.containsKey(PROPERTY_LINE_WIDTH)) {
				csb.setBarWidth(TiConvert.toInt(props.get(PROPERTY_LINE_WIDTH)));
			}

			if (props.containsKey(PROPERTY_COLOR_FILLED)) {
				csb.setProgressColor(TiConvert.toColor(props
						.getString(PROPERTY_COLOR_FILLED)));
			}

			if (props.containsKey(PROPERTY_COLOR_UNFILLED)) {
				csb.setRingBackgroundColor(TiConvert.toColor(props
						.getString(PROPERTY_COLOR_UNFILLED)));
			}

			Log.d(LCAT, "[VIEW LIFECYCLE EVENT] processProperties " + props);

		}

		@Override
		public void propertyChanged(String key, Object oldValue, Object newValue,
				KrollProxy proxy) {
			// This method is called whenever a proxy property value is updated.
			// Note that this
			// method is only called if the new value is different than the current
			// value.

			super.propertyChanged(key, oldValue, newValue, proxy);

			Log.d(LCAT, "[VIEW LIFECYCLE EVENT] propertyChanged: " + key + ' '
					+ oldValue + ' ' + newValue);
		}

		private void notifyOfChange(int newValue) {
			// The event listeners for a view are actually attached to the view
			// proxy.
			// You must reference 'proxy' to get the proxy for this view.

			Log.d(LCAT, "[VIEW LIFECYCLE EVENT] notifyOfValueChange");

			if (proxy.hasListeners("change")) {
				HashMap<String, Integer> hm = new HashMap<String, Integer>();
				hm.put("value", newValue);
				proxy.fireEvent("change", hm);
			}
		}

	}


}
