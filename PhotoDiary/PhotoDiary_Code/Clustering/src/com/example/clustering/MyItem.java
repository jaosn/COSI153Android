/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.clustering;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class MyItem implements ClusterItem {
	private LatLng mPosition;
	private String uri;
	private String date;

	public MyItem(double lat, double lng, String uri, String date) {
		mPosition = new LatLng(lat, lng);
		this.uri = uri;
		this.date = date;
	}

	@Override
	public LatLng getPosition() {
		return mPosition;
	}

	public void setPosition(String lat, String lng) {
		this.mPosition = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
	}

	public void addUri(String uri) {
		this.uri = uri;
	}

	public String getUri() {
		return uri;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDate() {
		return this.date;
	}

}
