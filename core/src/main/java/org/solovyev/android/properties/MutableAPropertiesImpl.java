/*
 * Copyright 2013 serso aka se.solovyev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Contact details
 *
 * Email: se.solovyev@gmail.com
 * Site:  http://se.solovyev.org
 */

package org.solovyev.android.properties;

import org.solovyev.common.JObject;
import org.solovyev.common.clone.Cloneables;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * User: serso
 * Date: 8/21/12
 * Time: 2:18 PM
 */
class MutableAPropertiesImpl extends JObject implements MutableAProperties {

	@Nonnull
	private Map<String, AProperty> properties = new HashMap<String, AProperty>();

	public MutableAPropertiesImpl() {
	}

	@Nonnull
	static MutableAProperties copyOf(@Nonnull MutableAProperties propertiesContainer) {
		return propertiesContainer.clone();
	}

	@Nonnull
	static MutableAProperties newInstance(@Nonnull Collection<AProperty> properties) {
		final MutableAPropertiesImpl result = new MutableAPropertiesImpl();

		for (AProperty property : properties) {
			result.setProperty(property);
		}

		return result;
	}

	@Nonnull
	static MutableAProperties newInstance(@Nonnull Map<String, AProperty> properties) {
		final MutableAPropertiesImpl result = new MutableAPropertiesImpl();

		for (AProperty property : properties.values()) {
			result.setProperty(property);
		}

		return result;
	}

	@Nonnull
	@Override
	public MutableAPropertiesImpl clone() {
		final MutableAPropertiesImpl clone = (MutableAPropertiesImpl) super.clone();

		clone.properties = Cloneables.cloneMap(this.properties);

		return clone;
	}

	@Override
	@Nonnull
	public AProperty setProperty(@Nonnull String name, @Nonnull String value) {
		final AProperty property = APropertyImpl.newInstance(name, value);

		properties.put(name, property);

		return property;
	}

	@Override
	public void setProperty(@Nonnull AProperty property) {
		properties.put(property.getName(), property);
	}

	@Override
	public void setPropertiesFrom(@Nonnull MutableAProperties that) {
		for (AProperty property : that.getProperties().values()) {
			setProperty(property);
		}
	}

	@Override
	public void setPropertiesFrom(@Nonnull Collection<AProperty> properties) {
		for (AProperty property : properties) {
			setProperty(property);
		}
	}

	@Override
	@Nullable
	public AProperty removeProperty(@Nonnull String name) {
		return properties.remove(name);
	}

	@Override
	@Nullable
	public AProperty getProperty(@Nonnull String name) {
		return properties.get(name);
	}

	@Override
	public String getPropertyValue(@Nonnull String name) {
		final AProperty property = properties.get(name);
		return property == null ? null : property.getValue();
	}

	@Override
	public void clearProperties() {
		properties.clear();
	}

	@Override
	@Nonnull
	public Map<String, AProperty> getProperties() {
		return Collections.unmodifiableMap(this.properties);
	}

	@Override
	@Nonnull
	public Collection<AProperty> getPropertiesCollection() {
		return Collections.unmodifiableCollection(this.properties.values());
	}
}
