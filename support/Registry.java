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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_8;
import static support.Format.formatCommandLine;

public class Registry {

    public static final String REGISTRY_ENV_PATH = "HKLM\\System\\CurrentControlSet\\Control\\Session Manager\\Environment";

    public static Optional<String> queryRegistry(String path, String key) throws Exception {
        ArrayList<String> cline = new ArrayList<>(Arrays.asList(
                System.getenv("WINDIR") + "/system32/reg.exe",
                "query",
                path
        ));
        if (!key.isEmpty()) {
            cline.add("/v");
            cline.add(key);
        } else {
            cline.add("/ve");
        }

        System.out.println("Spawning req process, command line: [" + formatCommandLine(cline) + "]");
        Path output = Paths.get("reg_out.txt");
        int code = new ProcessBuilder(cline)
                .inheritIO()
                .redirectOutput(output.toFile())
                .start()
                .waitFor();

        if (1 == code) {
            return Optional.empty();
        }

        if (0 != code) {
            throw new Exception("Reg query failure, code: [" + code + "]");
        }

        List<String> lines = Files.readAllLines(output, UTF_8);
        lines.removeIf(String::isEmpty);
        if (2 != lines.size()) {
            throw new Exception("Reg query invalid output, entries: [" + lines + "]");
        }

        String[] parts = lines.get(1).split(" {4}");

        String res = parts[parts.length - 1];

        if ("REG_SZ".equals(res)) {
            return Optional.of("");
        }

        return Optional.of(res);
    }
}
