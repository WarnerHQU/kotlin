/*
 * Copyright 2010-2017 JetBrains s.r.o.
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

package org.jetbrains.kotlin.resolve.jvm

import org.jetbrains.kotlin.descriptors.ClassOrPackageFragmentDescriptor
import org.jetbrains.kotlin.load.kotlin.FileBasedKotlinClass
import org.jetbrains.kotlin.load.kotlin.KotlinJvmBinarySourceElement
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedClassDescriptor

fun ClassOrPackageFragmentDescriptor.getClassVersion(generatingVersion: Int, throwErrors: Boolean): Int {
    if (this is DeserializedClassDescriptor /*|| this is DeserializedPackageFragment*/) {
        val source = this.source
        if (source is KotlinJvmBinarySourceElement) {
            val binaryClass = source.binaryClass

            if (binaryClass is FileBasedKotlinClass) {
                return binaryClass.classVersion
            }
            else if (throwErrors) {
                assert(binaryClass is FileBasedKotlinClass) { "KotlinJvmBinaryClass should be subclass of FileBasedKotlinClass, but " + binaryClass }
            }
        }
    }

    return generatingVersion
}