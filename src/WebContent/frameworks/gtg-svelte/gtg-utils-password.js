console.log('starting gtg-utils-password.js');

import GtgUtilsTools from './gtg-utils-tooling.js';

// Helper class to handle password management
class GtgUtilsPassword {
    static get PWD_MIN_LENGTH() { return 10; };
    static get PWD_MAX_LENGTH() { return 256; };
    static get PWD_POLICY_LOWER() { return /[a-z]/; };
    static get PWD_POLICY_UPPER() { return /[A-Z]/; };
    static get PWD_POLICY_DIGIT() { return /[0-9]/; };
    static get PWD_POLICY_SPECIAL() { return /[\!@#$%^&*()\\[\]{}\-_+=~`|:;"'<>,./?]/; };
    static get PWD_POLICY_FULL() { return [
        GtgUtilsPassword.PWD_POLICY_LOWER,
        GtgUtilsPassword.PWD_POLICY_UPPER,
        GtgUtilsPassword.PWD_POLICY_DIGIT,
        GtgUtilsPassword.PWD_POLICY_SPECIAL
    ]; };

    // Check that password's length is bigger than required minimum
    static checkLength(pwd, lengMin, lengMax) {
        return (!GtgUtilsTools.isNull(pwd) && (lengMin <= pwd.length) && (lengMax >= pwd.length));
    };

    // return true if passwords are identical
    static checkEquality(pwd1, pwd2) {
        return (!GtgUtilsTools.isNull(pwd1) && !GtgUtilsTools.isNull(pwd2) && (pwd1 === pwd2));
        return (!GtgUtilsTools.isNull(pwd1) && !GtgUtilsTools.isNull(pwd2) && (pwd1 === pwd2));
    };

    // Check that password is strong enough, using regular expression
    static checkStrength(pwd, arrCplx) {
        let checked = true;
        let regEx;

        if (!GtgUtilsTools.isNull(pwd) && !GtgUtilsTools.isNull(arrCplx) && (0 < arrCplx.length)) {
            for (let pos = 0; (pos < arrCplx.length) && checked; pos ++) {
                regEx = new RegExp(arrCplx[pos]);
                checked = regEx.test(pwd);
            }
            return checked;
        }
        return false;
    };

    static checkStrongPwd(pwd) {
        // a strong password has a minimal length and must respect hardest complexity
        return GtgUtilsPassword.checkLength(pwd, GtgUtilsPassword.PWD_MIN_LENGTH, GtgUtilsPassword.PWD_MAX_LENGTH) && GtgUtilsPassword.checkStrength(pwd, GtgUtilsPassword.PWD_POLICY_FULL);
    }
};

export default GtgUtilsPassword;

console.log('done gtg-utils-password.js');

/*
Copyright 2022 / Gobo the Geek

This file is part of "LoFiDroX".

    "LoFiDroX" is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    "LoFiDroX" is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with "HomeGed". If not, see <https://www.gnu.org/licenses/>.
*/
