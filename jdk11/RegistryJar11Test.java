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

public class RegistryJar11Test {

    public static void main(String[] args) throws Exception {
        install("ADDLOCAL=jdk_registry_jar");
        try {

            String scratchDir = Paths.get("").toAbsolutePath().toString();
            assertRegKey("HKLM\\Software\\Classes\\.jar",
                    "", "JARFile");
            assertRegKey("HKLM\\Software\\Classes\\.jar",
                    "Content Type", "application/java-archive");
            assertRegKey("HKLM\\Software\\Classes\\JARFile",
                    "", "JAR File");
            assertRegKey("HKLM\\Software\\Classes\\JARFile",
                    "EditFlags", "0x10000");
            //assertRegKey("HKLM\\Software\\Classes\\JARFile\\Shell\\Open",
            //        "", "&amp;Launch with ${${PROJECT_NAME}_VENDOR_SHORT} OpenJDK");
            assertRegKey("HKLM\\Software\\Classes\\JARFile\\Shell\\Open\\Command",
                    "", "\"" + scratchDir + "\\jdk\\bin\\javaw.exe\" -jar \"%1\" %*");
            assertPath("jdk/bin/java.exe");
            assertPath("jdk/bin/server/jvm.dll");
            assertPath("jdk/lib/modules");
            assertNoPath("jdk/missioncontrol");

        } finally {
            uninstall();
        }
    }
}
