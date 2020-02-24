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

import support.AppData;
import support.IO;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import static java.io.File.pathSeparator;
import static java.util.Collections.singletonList;
import static java.util.Collections.synchronizedSet;
import static support.AppData.findOutAppDataDir;
import static support.Assert.*;
import static support.IO.deleteDirectory;
import static support.Install.*;
import static support.Uninstall.uninstall;

/**
 * @test
 * @library ..
 */

public class UpdateWebstartMigrateTest {

    public static void main(String[] args) throws Exception {
        Path scratchDir = Paths.get("").toAbsolutePath();
        String prevMsiPath = System.getenv(TESTJDK_PREV_MSI_PATH);
        String msiPath = System.getenv(TESTJDK_MSI_PATH);
        Path prevAppDataDir = findOutAppDataDir(prevMsiPath);
        Path appDataDir = findOutAppDataDir(msiPath);

        // cleanup
        if (Files.exists(prevAppDataDir)) {
            deleteDirectory(prevAppDataDir);
        }
        if (Files.exists(appDataDir)) {
            deleteDirectory(appDataDir);
        }

        // install prev
        install(prevMsiPath, singletonList("ADDLOCAL=jdk,webstart,webstart_migrate"));

        // spawn webstart to populate appdata
        new ProcessBuilder(scratchDir + "/jdk/webstart/javaws.exe", "fail.jnlp").inheritIO().start().waitFor();

        // update
        install();
        try {

            assertPath("jdk/webstart/javaws.exe");
            assertPath("jdk/jre/bin/java.exe");
            assertPath("jdk/jre/bin/server/jvm.dll");
            assertNoPath("jdk/bin");

            assertNoPath(prevAppDataDir.toString());
            assertPath(appDataDir.toString());
            assertPath(appDataDir.toString() + "/webstart/.cache");
            assertPath(appDataDir.toString() + "/webstart/.config");

        } finally {
            uninstall();
        }
    }
}
