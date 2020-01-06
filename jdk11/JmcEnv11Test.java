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
import static support.Registry.REGISTRY_ENV_PATH;
import static support.Registry.queryRegistry;
import static support.Uninstall.uninstall;

/**
 * @test
 * @library ..
 */

public class JmcEnv11Test {

    public static void main(String[] args) throws Exception {
        install("ADDLOCAL=jmc_env");

        String scratchDir = Paths.get("").toAbsolutePath().toString();
        String pathVar = queryRegistry(REGISTRY_ENV_PATH, "PATH").get();
        assertThat(pathVar, pathVar.endsWith(scratchDir + "\\jdk\\missioncontrol\\"));
        assertNoRegKey(REGISTRY_ENV_PATH, "JAVA_HOME");
        assertNoRegKey(REGISTRY_ENV_PATH, "OJDKBUILD_JAVA_HOME");
        assertNoRegKey(REGISTRY_ENV_PATH, "REDHAT_JAVA_HOME");
        assertPath("jdk/missioncontrol");
        assertNoPath("jdk/bin");

        uninstall();
    }
}
