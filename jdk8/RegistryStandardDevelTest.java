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

import java.nio.file.Paths;

import static support.Assert.*;
import static support.Install.install;
import static support.Uninstall.uninstall;

/**
 * @test
 * @library ..
 */

public class RegistryStandardDevelTest {

    public static void main(String[] args) throws Exception {
        install("ADDLOCAL=jdk_registry_standard_devel");
        try {

            String scratchDir = Paths.get("").toAbsolutePath().toString();
            assertRegKey("HKLM\\Software\\JavaSoft\\Java Development Kit",
                    "CurrentVersion", "1.8");
            assertRegKey("HKLM\\Software\\JavaSoft\\Java Development Kit\\1.8",
                    "JavaHome", scratchDir + "\\jdk\\");
            assertRegKey("HKLM\\Software\\JavaSoft\\Java Development Kit\\1.8",
                    "RuntimeLib", scratchDir + "\\jdk\\jre\\bin\\server\\jvm.dll");
            assertNoRegKey("HKLM\\Software\\JavaSoft\\Java Runtime Environment", "CurrentVersion");
            assertNoRegKey("HKLM\\Software\\JavaSoft\\Java Runtime Environment\\1.8", "JavaHome");
            assertNoRegKey("HKLM\\Software\\JavaSoft\\Java Runtime Environment\\1.8", "RuntimeLib");
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
