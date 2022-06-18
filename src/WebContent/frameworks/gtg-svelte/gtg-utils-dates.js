console.log('starting gtg-utils-dates.js');

import GtgUtilsTools from './gtg-utils-tooling.js';
import moment from '../momentjs/moment.min.js';

class GtgUtilsDates {
    // return true is value is null or undefined
    static toDateHour(value) {
        if (!GtgUtilsTools.isNull(value)) {
            return moment(value).format('DD.MM.YYYY HH:mms:ss');
        }
        return null;
    };
};

export default GtgUtilsDates;

console.log('done gtg-utils-dates.js');

