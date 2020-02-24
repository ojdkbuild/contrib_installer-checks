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

import static support.Assert.*;
import static support.Install.install;
import static support.Uninstall.uninstall;

/**
 * @test
 * @library ..
 */

public class WebstartTest {

    public static void main(String[] args) throws Exception {
        install("ADDLOCAL=webstart");
        try {

            assertPath("jdk/webstart/javaws.exe");
            assertPath("jdk/webstart/javaws.jar");
            assertPath("jdk/webstart/javaws_options.txt");
            assertPath("jdk/webstart/javaws_splash.png");
            assertNoPath("jdk/jre");
            assertNoPath("jdk/bin");
            assertNoPath("jdk/update");

        } finally {
            uninstall();
        }
    }
}
