/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.doma.jdbc;

/**
 * 値をSQLのログ用文字列に変換するフォーマッタです。
 * 
 * @author taedium
 * 
 */
public interface SqlLogFormatter<T> {

    /**
     * 値をSQLのログ用文字列に変換します。
     * 
     * @param value
     *            値
     * @return ログ用文字列
     */
    String convertToLogFormat(T value);
}