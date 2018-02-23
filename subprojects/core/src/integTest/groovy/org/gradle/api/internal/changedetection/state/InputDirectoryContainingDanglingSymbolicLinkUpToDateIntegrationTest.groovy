/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.api.internal.changedetection.state

import org.gradle.test.fixtures.file.TestFile

class InputDirectoryContainingDanglingSymbolicLinkUpToDateIntegrationTest extends AbstractSymbolicLinkUpToDateIntegrationTest {
    @Override
    void makeScenarioProject() {
        buildFile << '''
            task checkCreated {
                inputs.dir file('inputs')
                outputs.file file("$buildDir/output.txt")
                doLast {
                    buildDir.mkdir()
                    file("$buildDir/output.txt").createNewFile()
                }
            }
        '''

        testDirectory.createDir('inputs').mkdirs()
        symbolicLinkUnderTest.createLink(relativeTarget)
    }

    @Override
    TestFile getSymbolicLinkUnderTest() {
        return file('inputs/sym-link')
    }

    @Override
    TestFile getTargetFile() {
        return file('some-missing-file-system-element')
    }

    @Override
    TestFile getAlternateTargetFile() {
        return file('some-other-missing-file-system-element')
    }
}
