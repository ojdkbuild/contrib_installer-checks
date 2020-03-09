/*
 * Copyright 2020, akashche at redhat.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package support;

public class Optional<T> {
    private final T t;

    private Optional(T t) {
        this.t = t;
    }

    public boolean isPresent() {
        return null != t;
    }

    public T get() {
        return t;
    }

    public static <T> Optional<T> of(T t) {
        return new Optional<T>(t);
    }

    public static <T> Optional<T> empty() {
        return new Optional<T>(null);
    }
}
