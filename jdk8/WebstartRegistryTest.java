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

import java.nio.file.Paths;

import static support.Assert.*;
import static support.Install.install;
import static support.Uninstall.uninstall;

/**
 * @test
 * @library ..
 */

public class WebstartRegistryTest {

    public static void main(String[] args) throws Exception {
        install("ADDLOCAL=webstart_registry");
        try {

            String scratchDir = Paths.get("").toAbsolutePath().toString();
            assertRegKey("HKLM\\Software\\Classes\\.jnlp",
                    "", "JNLPFile");
            assertRegKey("HKLM\\Software\\Classes\\jnlp",
                    "", "URL:jnlp Protocol");
            assertRegKey("HKLM\\Software\\Classes\\jnlp",
                    "URL Protocol", "");
            assertRegKey("HKLM\\Software\\Classes\\jnlp\\Shell\\Open\\Command",
                    "", "\"" + scratchDir + "\\jdk\\webstart\\javaws.exe\" \"%1\"");
            assertRegKey("HKLM\\Software\\Classes\\jnlps",
                    "", "URL:jnlps Protocol");
            assertRegKey("HKLM\\Software\\Classes\\jnlps",
                    "URL Protocol", "");
            assertRegKey("HKLM\\Software\\Classes\\jnlps\\Shell\\Open\\Command",
                    "", "\"" + scratchDir + "\\jdk\\webstart\\javaws.exe\" \"%1\"");
            assertRegKey("HKLM\\Software\\Classes\\JNLPFile",
                    "", "JNLP File");
            assertRegKey("HKLM\\Software\\Classes\\JNLPFile",
                    "EditFlags", "0x10000");
            //assertRegKey("HKLM\\Software\\Classes\\JNLPFile\\Shell\\Open",
            //        "", "&amp;Launch with ${openjdk_VENDOR_SHORT} WebStart");
            assertRegKey("HKLM\\Software\\Classes\\JNLPFile\\Shell\\Open\\Command",
                    "", "\"" + scratchDir + "\\jdk\\webstart\\javaws.exe\" \"%1\"");
            assertPath("jdk/webstart");
            assertNoPath("jdk/jre");
            assertNoPath("jdk/bin");
            assertNoPath("jdk/update");

        } finally {
            uninstall();
        }
    }
}
