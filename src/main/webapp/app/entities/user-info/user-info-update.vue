<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="etaxApp.userInfo.home.createOrEditLabel" v-text="$t('etaxApp.userInfo.home.createOrEditLabel')">Create or edit a UserInfo</h2>
                <div>
                    <div class="form-group" v-if="userInfo.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="userInfo.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.userInfo.cstdAdmSection')" for="user-info-cstdAdmSection">Cstd Adm Section</label>
                        <input type="text" class="form-control" name="cstdAdmSection" id="user-info-cstdAdmSection"
                            :class="{'valid': !$v.userInfo.cstdAdmSection.$invalid, 'invalid': $v.userInfo.cstdAdmSection.$invalid }" v-model="$v.userInfo.cstdAdmSection.$model"  required/>
                        <div v-if="$v.userInfo.cstdAdmSection.$anyDirty && $v.userInfo.cstdAdmSection.$invalid">
                            <small class="form-text text-danger" v-if="!$v.userInfo.cstdAdmSection.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.userInfo.cstdAdmSection.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 40 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.userInfo.cstdSecurityLevel')" for="user-info-cstdSecurityLevel">Cstd Security Level</label>
                        <input type="text" class="form-control" name="cstdSecurityLevel" id="user-info-cstdSecurityLevel"
                            :class="{'valid': !$v.userInfo.cstdSecurityLevel.$invalid, 'invalid': $v.userInfo.cstdSecurityLevel.$invalid }" v-model="$v.userInfo.cstdSecurityLevel.$model"  required/>
                        <div v-if="$v.userInfo.cstdSecurityLevel.$anyDirty && $v.userInfo.cstdSecurityLevel.$invalid">
                            <small class="form-text text-danger" v-if="!$v.userInfo.cstdSecurityLevel.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.userInfo.cstdSecurityLevel.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 40 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.userInfo.cstdUserType')" for="user-info-cstdUserType">Cstd User Type</label>
                        <input type="text" class="form-control" name="cstdUserType" id="user-info-cstdUserType"
                            :class="{'valid': !$v.userInfo.cstdUserType.$invalid, 'invalid': $v.userInfo.cstdUserType.$invalid }" v-model="$v.userInfo.cstdUserType.$model"  required/>
                        <div v-if="$v.userInfo.cstdUserType.$anyDirty && $v.userInfo.cstdUserType.$invalid">
                            <small class="form-text text-danger" v-if="!$v.userInfo.cstdUserType.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.userInfo.cstdUserType.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 40 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.userInfo.description')" for="user-info-description">Description</label>
                        <input type="text" class="form-control" name="description" id="user-info-description"
                            :class="{'valid': !$v.userInfo.description.$invalid, 'invalid': $v.userInfo.description.$invalid }" v-model="$v.userInfo.description.$model" />
                        <div v-if="$v.userInfo.description.$anyDirty && $v.userInfo.description.$invalid">
                            <small class="form-text text-danger" v-if="!$v.userInfo.description.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 200 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.userInfo.middleName')" for="user-info-middleName">Middle Name</label>
                        <input type="text" class="form-control" name="middleName" id="user-info-middleName"
                            :class="{'valid': !$v.userInfo.middleName.$invalid, 'invalid': $v.userInfo.middleName.$invalid }" v-model="$v.userInfo.middleName.$model" />
                        <div v-if="$v.userInfo.middleName.$anyDirty && $v.userInfo.middleName.$invalid">
                            <small class="form-text text-danger" v-if="!$v.userInfo.middleName.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 50 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.userInfo.gender')" for="user-info-gender">Gender</label>
                        <input type="text" class="form-control" name="gender" id="user-info-gender"
                            :class="{'valid': !$v.userInfo.gender.$invalid, 'invalid': $v.userInfo.gender.$invalid }" v-model="$v.userInfo.gender.$model"  required/>
                        <div v-if="$v.userInfo.gender.$anyDirty && $v.userInfo.gender.$invalid">
                            <small class="form-text text-danger" v-if="!$v.userInfo.gender.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.userInfo.gender.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 40 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.userInfo.phoneNum')" for="user-info-phoneNum">Phone Num</label>
                        <input type="text" class="form-control" name="phoneNum" id="user-info-phoneNum"
                            :class="{'valid': !$v.userInfo.phoneNum.$invalid, 'invalid': $v.userInfo.phoneNum.$invalid }" v-model="$v.userInfo.phoneNum.$model" />
                        <div v-if="$v.userInfo.phoneNum.$anyDirty && $v.userInfo.phoneNum.$invalid">
                            <small class="form-text text-danger" v-if="!$v.userInfo.phoneNum.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 50 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.userInfo.faxNum')" for="user-info-faxNum">Fax Num</label>
                        <input type="text" class="form-control" name="faxNum" id="user-info-faxNum"
                            :class="{'valid': !$v.userInfo.faxNum.$invalid, 'invalid': $v.userInfo.faxNum.$invalid }" v-model="$v.userInfo.faxNum.$model" />
                        <div v-if="$v.userInfo.faxNum.$anyDirty && $v.userInfo.faxNum.$invalid">
                            <small class="form-text text-danger" v-if="!$v.userInfo.faxNum.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 50 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.userInfo.effiectiveDate')" for="user-info-effiectiveDate">Effiective Date</label>
                        <div class="d-flex">
                            <input id="user-info-effiectiveDate" type="datetime-local" class="form-control" name="effiectiveDate" :class="{'valid': !$v.userInfo.effiectiveDate.$invalid, 'invalid': $v.userInfo.effiectiveDate.$invalid }"
                            
                            :value="convertDateTimeFromServer($v.userInfo.effiectiveDate.$model)"
                            @change="updateZonedDateTimeField('effiectiveDate', $event)"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.userInfo.expiryDate')" for="user-info-expiryDate">Expiry Date</label>
                        <div class="d-flex">
                            <input id="user-info-expiryDate" type="datetime-local" class="form-control" name="expiryDate" :class="{'valid': !$v.userInfo.expiryDate.$invalid, 'invalid': $v.userInfo.expiryDate.$invalid }"
                            
                            :value="convertDateTimeFromServer($v.userInfo.expiryDate.$model)"
                            @change="updateZonedDateTimeField('expiryDate', $event)"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.userInfo.blocked')" for="user-info-blocked">Blocked</label>
                        <input type="checkbox" class="form-check" name="blocked" id="user-info-blocked"
                            :class="{'valid': !$v.userInfo.blocked.$invalid, 'invalid': $v.userInfo.blocked.$invalid }" v-model="$v.userInfo.blocked.$model"  required/>
                        <div v-if="$v.userInfo.blocked.$anyDirty && $v.userInfo.blocked.$invalid">
                            <small class="form-text text-danger" v-if="!$v.userInfo.blocked.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.userInfo.blockedReason')" for="user-info-blockedReason">Blocked Reason</label>
                        <input type="text" class="form-control" name="blockedReason" id="user-info-blockedReason"
                            :class="{'valid': !$v.userInfo.blockedReason.$invalid, 'invalid': $v.userInfo.blockedReason.$invalid }" v-model="$v.userInfo.blockedReason.$model"  required/>
                        <div v-if="$v.userInfo.blockedReason.$anyDirty && $v.userInfo.blockedReason.$invalid">
                            <small class="form-text text-danger" v-if="!$v.userInfo.blockedReason.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.userInfo.blockedReason.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 10 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.userInfo.forcedPwdChange')" for="user-info-forcedPwdChange">Forced Pwd Change</label>
                        <input type="checkbox" class="form-check" name="forcedPwdChange" id="user-info-forcedPwdChange"
                            :class="{'valid': !$v.userInfo.forcedPwdChange.$invalid, 'invalid': $v.userInfo.forcedPwdChange.$invalid }" v-model="$v.userInfo.forcedPwdChange.$model"  required/>
                        <div v-if="$v.userInfo.forcedPwdChange.$anyDirty && $v.userInfo.forcedPwdChange.$invalid">
                            <small class="form-text text-danger" v-if="!$v.userInfo.forcedPwdChange.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.userInfo.cstdTitles')" for="user-info-cstdTitles">Cstd Titles</label>
                        <input type="text" class="form-control" name="cstdTitles" id="user-info-cstdTitles"
                            :class="{'valid': !$v.userInfo.cstdTitles.$invalid, 'invalid': $v.userInfo.cstdTitles.$invalid }" v-model="$v.userInfo.cstdTitles.$model"  required/>
                        <div v-if="$v.userInfo.cstdTitles.$anyDirty && $v.userInfo.cstdTitles.$invalid">
                            <small class="form-text text-danger" v-if="!$v.userInfo.cstdTitles.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.userInfo.cstdTitles.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 40 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.userInfo.cstdStatus')" for="user-info-cstdStatus">Cstd Status</label>
                        <input type="text" class="form-control" name="cstdStatus" id="user-info-cstdStatus"
                            :class="{'valid': !$v.userInfo.cstdStatus.$invalid, 'invalid': $v.userInfo.cstdStatus.$invalid }" v-model="$v.userInfo.cstdStatus.$model"  required/>
                        <div v-if="$v.userInfo.cstdStatus.$anyDirty && $v.userInfo.cstdStatus.$invalid">
                            <small class="form-text text-danger" v-if="!$v.userInfo.cstdStatus.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.userInfo.cstdStatus.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 40 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.userInfo.cstdAdmDivsison')" for="user-info-cstdAdmDivsison">Cstd Adm Divsison</label>
                        <input type="text" class="form-control" name="cstdAdmDivsison" id="user-info-cstdAdmDivsison"
                            :class="{'valid': !$v.userInfo.cstdAdmDivsison.$invalid, 'invalid': $v.userInfo.cstdAdmDivsison.$invalid }" v-model="$v.userInfo.cstdAdmDivsison.$model"  required/>
                        <div v-if="$v.userInfo.cstdAdmDivsison.$anyDirty && $v.userInfo.cstdAdmDivsison.$invalid">
                            <small class="form-text text-danger" v-if="!$v.userInfo.cstdAdmDivsison.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.userInfo.cstdAdmDivsison.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 40 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.userInfo.loginStatus')" for="user-info-loginStatus">Login Status</label>
                        <input type="text" class="form-control" name="loginStatus" id="user-info-loginStatus"
                            :class="{'valid': !$v.userInfo.loginStatus.$invalid, 'invalid': $v.userInfo.loginStatus.$invalid }" v-model="$v.userInfo.loginStatus.$model" />
                        <div v-if="$v.userInfo.loginStatus.$anyDirty && $v.userInfo.loginStatus.$invalid">
                            <small class="form-text text-danger" v-if="!$v.userInfo.loginStatus.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 20 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.userInfo.loginTime')" for="user-info-loginTime">Login Time</label>
                        <div class="d-flex">
                            <input id="user-info-loginTime" type="datetime-local" class="form-control" name="loginTime" :class="{'valid': !$v.userInfo.loginTime.$invalid, 'invalid': $v.userInfo.loginTime.$invalid }"
                            
                            :value="convertDateTimeFromServer($v.userInfo.loginTime.$model)"
                            @change="updateZonedDateTimeField('loginTime', $event)"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.userInfo.attempt')" for="user-info-attempt">Attempt</label>
                        <input type="number" class="form-control" name="attempt" id="user-info-attempt"
                            :class="{'valid': !$v.userInfo.attempt.$invalid, 'invalid': $v.userInfo.attempt.$invalid }" v-model.number="$v.userInfo.attempt.$model"  required/>
                        <div v-if="$v.userInfo.attempt.$anyDirty && $v.userInfo.attempt.$invalid">
                            <small class="form-text text-danger" v-if="!$v.userInfo.attempt.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.userInfo.attempt.maxLength" v-bind:value="$t('entity.validation.max')">
                                This field cannot be longer than 3 characters.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.userInfo.attempt.number" v-text="$t('entity.validation.number')">
                                This field should be a number.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.userInfo.needApprove')" for="user-info-needApprove">Need Approve</label>
                        <input type="checkbox" class="form-check" name="needApprove" id="user-info-needApprove"
                            :class="{'valid': !$v.userInfo.needApprove.$invalid, 'invalid': $v.userInfo.needApprove.$invalid }" v-model="$v.userInfo.needApprove.$model"  required/>
                        <div v-if="$v.userInfo.needApprove.$anyDirty && $v.userInfo.needApprove.$invalid">
                            <small class="form-text text-danger" v-if="!$v.userInfo.needApprove.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.userInfo.logoutTime')" for="user-info-logoutTime">Logout Time</label>
                        <div class="d-flex">
                            <input id="user-info-logoutTime" type="datetime-local" class="form-control" name="logoutTime" :class="{'valid': !$v.userInfo.logoutTime.$invalid, 'invalid': $v.userInfo.logoutTime.$invalid }"
                            
                            :value="convertDateTimeFromServer($v.userInfo.logoutTime.$model)"
                            @change="updateZonedDateTimeField('logoutTime', $event)"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.userInfo.nationalId')" for="user-info-nationalId">National Id</label>
                        <input type="text" class="form-control" name="nationalId" id="user-info-nationalId"
                            :class="{'valid': !$v.userInfo.nationalId.$invalid, 'invalid': $v.userInfo.nationalId.$invalid }" v-model="$v.userInfo.nationalId.$model"  required/>
                        <div v-if="$v.userInfo.nationalId.$anyDirty && $v.userInfo.nationalId.$invalid">
                            <small class="form-text text-danger" v-if="!$v.userInfo.nationalId.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.userInfo.nationalId.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 10 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.userInfo.cstdOrganizationGrade')" for="user-info-cstdOrganizationGrade">Cstd Organization Grade</label>
                        <input type="text" class="form-control" name="cstdOrganizationGrade" id="user-info-cstdOrganizationGrade"
                            :class="{'valid': !$v.userInfo.cstdOrganizationGrade.$invalid, 'invalid': $v.userInfo.cstdOrganizationGrade.$invalid }" v-model="$v.userInfo.cstdOrganizationGrade.$model" />
                        <div v-if="$v.userInfo.cstdOrganizationGrade.$anyDirty && $v.userInfo.cstdOrganizationGrade.$invalid">
                            <small class="form-text text-danger" v-if="!$v.userInfo.cstdOrganizationGrade.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 40 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.userInfo.cstdEmploymentType')" for="user-info-cstdEmploymentType">Cstd Employment Type</label>
                        <input type="text" class="form-control" name="cstdEmploymentType" id="user-info-cstdEmploymentType"
                            :class="{'valid': !$v.userInfo.cstdEmploymentType.$invalid, 'invalid': $v.userInfo.cstdEmploymentType.$invalid }" v-model="$v.userInfo.cstdEmploymentType.$model" />
                        <div v-if="$v.userInfo.cstdEmploymentType.$anyDirty && $v.userInfo.cstdEmploymentType.$invalid">
                            <small class="form-text text-danger" v-if="!$v.userInfo.cstdEmploymentType.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 40 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.userInfo.manuScript')" for="user-info-manuScript">Manu Script</label>
                        <div>
                            <img v-bind:src="'data:' + userInfo.manuScriptContentType + ';base64,' + userInfo.manuScript" style="max-height: 100px;" v-if="userInfo.manuScript" alt="userInfo image"/>
                            <div v-if="userInfo.manuScript" class="form-text text-danger clearfix">
                                <span class="pull-left">{{userInfo.manuScriptContentType}}, {{byteSize(userInfo.manuScript)}}</span>
                                <button type="button" v-on:click="clearInputImage('manuScript', 'manuScriptContentType', 'file_manuScript')" class="btn btn-secondary btn-xs pull-right">
                                    <font-awesome-icon icon="times"></font-awesome-icon>
                                </button>
                            </div>
                            <input type="file" ref="file_manuScript" id="file_manuScript" v-on:change="setFileData($event, userInfo, 'manuScript', true)" accept="image/*" v-text="$t('entity.action.addimage')"/>
                        </div>
                        <input type="hidden" class="form-control" name="manuScript" id="user-info-manuScript"
                            :class="{'valid': !$v.userInfo.manuScript.$invalid, 'invalid': $v.userInfo.manuScript.$invalid }" v-model="$v.userInfo.manuScript.$model" />
                        <input type="hidden" class="form-control" name="manuScriptContentType" id="user-info-manuScriptContentType"
                            v-model="userInfo.manuScriptContentType" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.userInfo.ccVersion')" for="user-info-ccVersion">Cc Version</label>
                        <input type="number" class="form-control" name="ccVersion" id="user-info-ccVersion"
                            :class="{'valid': !$v.userInfo.ccVersion.$invalid, 'invalid': $v.userInfo.ccVersion.$invalid }" v-model.number="$v.userInfo.ccVersion.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('etaxApp.userInfo.user')" for="user-info-user">User</label>
                        <select class="form-control" id="user-info-user" name="user" v-model="userInfo.user">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="userInfo.user && userOption.id === userInfo.user.id ? userInfo.user : userOption" v-for="userOption in users" :key="userOption.id">{{userOption.login}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.userInfo.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./user-info-update.component.ts">
</script>
