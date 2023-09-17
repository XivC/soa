/*
 * Dybov's and Lavrov's SoA lab-1
 * Some dragons service
 *
 * OpenAPI spec version: 1.0.0
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 *
 * Swagger Codegen version: 3.0.46
 *
 * Do not edit the class manually.
 *
 */
import {ApiClient} from '../ApiClient.js';
import {Country} from './Country.js';

/**
 * The Person model module.
 * @module model/Person
 * @version 1.0.0
 */
export class Person {
  /**
   * Constructs a new <code>Person</code>.
   * @alias module:model/Person
   * @class
   */
  constructor() {
  }

  /**
   * Constructs a <code>Person</code> from a plain JavaScript object, optionally creating a new instance.
   * Copies all relevant properties from <code>data</code> to <code>obj</code> if supplied or a new instance if not.
   * @param {Object} data The plain JavaScript object bearing properties of interest.
   * @param {module:model/Person} obj Optional instance to populate.
   * @return {module:model/Person} The populated <code>Person</code> instance.
   */
  static constructFromObject(data, obj) {
    if (data) {
      obj = obj || new Person();
      if (data.hasOwnProperty('name'))
        obj.name = ApiClient.convertToType(data['name'], 'String');
      if (data.hasOwnProperty('height'))
        obj.height = ApiClient.convertToType(data['height'], 'Number');
      if (data.hasOwnProperty('weight'))
        obj.weight = ApiClient.convertToType(data['weight'], 'Number');
      if (data.hasOwnProperty('passportID'))
        obj.passportID = ApiClient.convertToType(data['passportID'], 'String');
      if (data.hasOwnProperty('nationality'))
        obj.nationality = Country.constructFromObject(data['nationality']);
    }
    return obj;
  }
}

/**
 * @member {String} name
 */
Person.prototype.name = undefined;

/**
 * @member {Number} height
 */
Person.prototype.height = undefined;

/**
 * @member {Number} weight
 */
Person.prototype.weight = undefined;

/**
 * @member {String} passportID
 */
Person.prototype.passportID = undefined;

/**
 * @member {module:model/Country} nationality
 */
Person.prototype.nationality = undefined;
