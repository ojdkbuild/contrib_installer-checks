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

import static support.Assert.assertNoPath;
import static support.Assert.assertPath;
import static support.Install.install;
import static support.Uninstall.uninstall;

/**
 * @test
 * @library ..
 */

public class JreOnlyTest {

    public static void main(String[] args) throws Exception {
        install("ADDLOCAL=jdk");
        try {

            assertPath("jdk/jre/bin/java.exe");
            assertPath("jdk/jre/bin/server/jvm.dll");
            assertNoPath("jdk/bin");
            assertNoPath("jdk/lib/tools.jar");
            assertNoPath("jdk/webstart");
            assertNoPath("jdk/update");

        } finally {
            uninstall();
        }
    }
}
