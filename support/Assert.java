/*
 * Copyright 2019, akashche at redhat.com
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

import java.nio.file.Files;
import java.nio.file.Paths;

import static support.Registry.queryRegistry;

public class Assert {

    public static void assertThat(String message, boolean condition) {
        if (!condition) {
            throw new AssertionError("Check failed, message: [" + message + "]");
        }
    }

    public static void assertFalse(String message, boolean condition) {
        assertThat(message, !condition);
    }

    public static void assertPath(String path) {
        assertThat(path, Files.exists(Paths.get(path)));
    }

    public static void assertNoPath(String path) {
        assertFalse(path, Files.exists(Paths.get(path)));
    }

    public static void assertEquals(String message, String expected, String actual) {
        if (!(null == expected || null == actual || expected.equals(actual))) {
            throw new AssertionError("Check failed, expected: [" + expected + "]," +
                    " actual: [" + actual + "], message: [" + message + "]");
        }
    }

    public static void assertRegKey(String path, String key, String expected) throws Exception {
        Optional<String> opt = queryRegistry(path, key);
        String msg = path + ":" + key;
        assertThat(msg, opt.isPresent());
        assertEquals(msg, expected, opt.get());
    }

    public static void assertNoRegKey(String path, String key) throws Exception {
        Optional<String> opt = queryRegistry(path, key);
        assertFalse(path + ":" + key, opt.isPresent());
    }
}
