/*******************************************************************************
 *    ___                  _   ____  ____
 *   / _ \ _   _  ___  ___| |_|  _ \| __ )
 *  | | | | | | |/ _ \/ __| __| | | |  _ \
 *  | |_| | |_| |  __/\__ \ |_| |_| | |_) |
 *   \__\_\\__,_|\___||___/\__|____/|____/
 *
 * Copyright (C) 2014-2019 Appsicle
 *
 * This program is free software: you can redistribute it and/or  modify
 * it under the terms of the GNU Affero General Public License, version 3,
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 ******************************************************************************/

package com.questdb.std.ex;

public class KerberosException extends Exception {
    private final int code;

    public KerberosException(int status) {
        super(toText(status));
        this.code = status;
    }

    private static String toText(int status) {
        switch (status) {
            case 0x80090300:
                return "SEC_E_INSUFFICIENT_MEMORY";
            case 0x80090304:
                return "SEC_E_INTERNAL_ERROR";
            case 0x80090301:
                return "SEC_E_INVALID_HANDLE";
            case 0x80090308:
                return "SEC_E_INVALID_TOKEN";
            case 0x8009030C:
                return "SEC_E_LOGON_DENIED";
            case 0x80090311:
                return "SEC_E_NO_AUTHENTICATING_AUTHORITY";
            case 0x8009030E:
                return "SEC_E_NO_CREDENTIALS";
            case 0x80090303:
                return "SEC_E_TARGET_UNKNOWN";
            case 0x80090302:
                return "SEC_E_UNSUPPORTED_FUNCTION";
            case 0x80090322:
                return "SEC_E_WRONG_PRINCIPAL";
            case 0x80090014:
                return "SEC_I_COMPLETE_AND_CONTINUE";
            case 0x80090013:
                return "SEC_I_COMPLETE_NEEDED";
            case 0x80090012:
                return "SEC_I_CONTINUE_NEEDED";
            case 0x80090305:
                return "SEC_E_SECPKG_NOT_FOUND";
            case 0x80090306:
                return "SEC_E_NOT_OWNER";
            case 0x80090307:
                return "SEC_E_CANNOT_INSTALL";
            case 0x80090309:
                return "SEC_E_CANNOT_PACK";
            case 0x8009030B:
                return "SEC_E_NO_IMPERSONATION";
            case 0x8009030D:
                return "SEC_E_UNKNOWN_CREDENTIALS";
            case 0x8009030F:
                return "SEC_E_MESSAGE_ALTERED";
            case 0x80090310:
                return "SEC_E_OUT_OF_SEQUENCE";
            case 0x80090312:
                return "SEC_E_CONTEXT_EXPIRED";
            case 0x80090313:
                return "SEC_E_INCOMPLETE_MESSAGE";
            case 0x80090015:
                return "SEC_I_LOCAL_LOGON";
            case 0x80090321:
                return "SEC_E_BUFFER_TOO_SMALL";
            default:
                return Integer.toHexString(status);

        }
    }

    public int getCode() {
        return code;
    }
}
