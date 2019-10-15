<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="userInfo">
                <h2 class="jh-entity-heading"><span v-text="$t('etaxApp.userInfo.detail.title')">UserInfo</span> {{userInfo.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('etaxApp.userInfo.cstdAdmSection')">Cstd Adm Section</span>
                    </dt>
                    <dd>
                        <span>{{userInfo.cstdAdmSection}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.userInfo.cstdSecurityLevel')">Cstd Security Level</span>
                    </dt>
                    <dd>
                        <span>{{userInfo.cstdSecurityLevel}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.userInfo.cstdUserType')">Cstd User Type</span>
                    </dt>
                    <dd>
                        <span>{{userInfo.cstdUserType}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.userInfo.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{userInfo.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.userInfo.middleName')">Middle Name</span>
                    </dt>
                    <dd>
                        <span>{{userInfo.middleName}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.userInfo.gender')">Gender</span>
                    </dt>
                    <dd>
                        <span>{{userInfo.gender}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.userInfo.phoneNum')">Phone Num</span>
                    </dt>
                    <dd>
                        <span>{{userInfo.phoneNum}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.userInfo.faxNum')">Fax Num</span>
                    </dt>
                    <dd>
                        <span>{{userInfo.faxNum}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.userInfo.effiectiveDate')">Effiective Date</span>
                    </dt>
                    <dd>
                        <span>{{userInfo.effiectiveDate | formatDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.userInfo.expiryDate')">Expiry Date</span>
                    </dt>
                    <dd>
                        <span>{{userInfo.expiryDate | formatDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.userInfo.blocked')">Blocked</span>
                    </dt>
                    <dd>
                        <span>{{userInfo.blocked}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.userInfo.blockedReason')">Blocked Reason</span>
                    </dt>
                    <dd>
                        <span>{{userInfo.blockedReason}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.userInfo.forcedPwdChange')">Forced Pwd Change</span>
                    </dt>
                    <dd>
                        <span>{{userInfo.forcedPwdChange}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.userInfo.cstdTitles')">Cstd Titles</span>
                    </dt>
                    <dd>
                        <span>{{userInfo.cstdTitles}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.userInfo.cstdStatus')">Cstd Status</span>
                    </dt>
                    <dd>
                        <span>{{userInfo.cstdStatus}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.userInfo.cstdAdmDivsison')">Cstd Adm Divsison</span>
                    </dt>
                    <dd>
                        <span>{{userInfo.cstdAdmDivsison}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.userInfo.loginStatus')">Login Status</span>
                    </dt>
                    <dd>
                        <span>{{userInfo.loginStatus}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.userInfo.officeCode')">Office Code</span>
                    </dt>
                    <dd>
                        <span>{{userInfo.officeCode}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.userInfo.loginTime')">Login Time</span>
                    </dt>
                    <dd>
                        <span>{{userInfo.loginTime | formatDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.userInfo.attempt')">Attempt</span>
                    </dt>
                    <dd>
                        <span>{{userInfo.attempt}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.userInfo.needApprove')">Need Approve</span>
                    </dt>
                    <dd>
                        <span>{{userInfo.needApprove}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.userInfo.logoutTime')">Logout Time</span>
                    </dt>
                    <dd>
                        <span>{{userInfo.logoutTime | formatDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.userInfo.nationalId')">National Id</span>
                    </dt>
                    <dd>
                        <span>{{userInfo.nationalId}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.userInfo.cstdOrganizationGrade')">Cstd Organization Grade</span>
                    </dt>
                    <dd>
                        <span>{{userInfo.cstdOrganizationGrade}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.userInfo.cstdEmploymentType')">Cstd Employment Type</span>
                    </dt>
                    <dd>
                        <span>{{userInfo.cstdEmploymentType}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.userInfo.manuScript')">Manu Script</span>
                    </dt>
                    <dd>
                        <div v-if="userInfo.manuScript">
                            <a v-on:click="openFile(userInfo.manuScriptContentType, userInfo.manuScript)">
                                <img v-bind:src="'data:' + userInfo.manuScriptContentType + ';base64,' + userInfo.manuScript" style="max-width: 100%;" alt="userInfo image"/>
                            </a>
                            {{userInfo.manuScriptContentType}}, {{byteSize(userInfo.manuScript)}}
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.userInfo.ccVersion')">Cc Version</span>
                    </dt>
                    <dd>
                        <span>{{userInfo.ccVersion}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.userInfo.user')">User</span>
                    </dt>
                    <dd>
                        {{userInfo.user ? userInfo.user.login : ''}}
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="userInfo.id" :to="{name: 'UserInfoEdit', params: {userInfoId: userInfo.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./user-info-details.component.ts">
</script>
