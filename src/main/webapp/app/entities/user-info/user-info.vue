<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('etaxApp.userInfo.home.title')" id="user-info-heading">User Infos</span>
            <router-link :to="{name: 'UserInfoCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-user-info">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('etaxApp.userInfo.home.createLabel')">
                    Create new UserInfo
                </span>
            </router-link>
        </h2>
        <b-alert :show="dismissCountDown"
            dismissible
            :variant="alertType"
            @dismissed="dismissCountDown=0"
            @dismiss-count-down="countDownChanged">
            {{alertMessage}}
        </b-alert>
        <br/>
        <div class="alert alert-warning" v-if="!isFetching && userInfos && userInfos.length === 0">
            <span v-text="$t('etaxApp.userInfo.home.notFound')">No userInfos found</span>
        </div>
        <div class="table-responsive" v-if="userInfos && userInfos.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('cstdAdmSection')"><span v-text="$t('etaxApp.userInfo.cstdAdmSection')">Cstd Adm Section</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('cstdSecurityLevel')"><span v-text="$t('etaxApp.userInfo.cstdSecurityLevel')">Cstd Security Level</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('cstdUserType')"><span v-text="$t('etaxApp.userInfo.cstdUserType')">Cstd User Type</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('description')"><span v-text="$t('etaxApp.userInfo.description')">Description</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('middleName')"><span v-text="$t('etaxApp.userInfo.middleName')">Middle Name</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('gender')"><span v-text="$t('etaxApp.userInfo.gender')">Gender</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('phoneNum')"><span v-text="$t('etaxApp.userInfo.phoneNum')">Phone Num</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('faxNum')"><span v-text="$t('etaxApp.userInfo.faxNum')">Fax Num</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('effiectiveDate')"><span v-text="$t('etaxApp.userInfo.effiectiveDate')">Effiective Date</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('expiryDate')"><span v-text="$t('etaxApp.userInfo.expiryDate')">Expiry Date</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('blocked')"><span v-text="$t('etaxApp.userInfo.blocked')">Blocked</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('blockedReason')"><span v-text="$t('etaxApp.userInfo.blockedReason')">Blocked Reason</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('forcedPwdChange')"><span v-text="$t('etaxApp.userInfo.forcedPwdChange')">Forced Pwd Change</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('cstdTitles')"><span v-text="$t('etaxApp.userInfo.cstdTitles')">Cstd Titles</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('cstdStatus')"><span v-text="$t('etaxApp.userInfo.cstdStatus')">Cstd Status</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('cstdAdmDivsison')"><span v-text="$t('etaxApp.userInfo.cstdAdmDivsison')">Cstd Adm Divsison</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('loginStatus')"><span v-text="$t('etaxApp.userInfo.loginStatus')">Login Status</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('loginTime')"><span v-text="$t('etaxApp.userInfo.loginTime')">Login Time</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('attempt')"><span v-text="$t('etaxApp.userInfo.attempt')">Attempt</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('needApprove')"><span v-text="$t('etaxApp.userInfo.needApprove')">Need Approve</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('logoutTime')"><span v-text="$t('etaxApp.userInfo.logoutTime')">Logout Time</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('nationalId')"><span v-text="$t('etaxApp.userInfo.nationalId')">National Id</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('cstdOrganizationGrade')"><span v-text="$t('etaxApp.userInfo.cstdOrganizationGrade')">Cstd Organization Grade</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('cstdEmploymentType')"><span v-text="$t('etaxApp.userInfo.cstdEmploymentType')">Cstd Employment Type</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('manuScript')"><span v-text="$t('etaxApp.userInfo.manuScript')">Manu Script</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('ccVersion')"><span v-text="$t('etaxApp.userInfo.ccVersion')">Cc Version</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('user.login')"><span v-text="$t('etaxApp.userInfo.user')">User</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="userInfo of orderBy(userInfos, propOrder, reverse === true ? 1 : -1)"
                    :key="userInfo.id">
                    <td>
                        <router-link :to="{name: 'UserInfoView', params: {userInfoId: userInfo.id}}">{{userInfo.id}}</router-link>
                    </td>
                    <td>{{userInfo.cstdAdmSection}}</td>
                    <td>{{userInfo.cstdSecurityLevel}}</td>
                    <td>{{userInfo.cstdUserType}}</td>
                    <td>{{userInfo.description}}</td>
                    <td>{{userInfo.middleName}}</td>
                    <td>{{userInfo.gender}}</td>
                    <td>{{userInfo.phoneNum}}</td>
                    <td>{{userInfo.faxNum}}</td>
                    <td>{{userInfo.effiectiveDate | formatDate}}</td>
                    <td>{{userInfo.expiryDate | formatDate}}</td>
                    <td>{{userInfo.blocked}}</td>
                    <td>{{userInfo.blockedReason}}</td>
                    <td>{{userInfo.forcedPwdChange}}</td>
                    <td>{{userInfo.cstdTitles}}</td>
                    <td>{{userInfo.cstdStatus}}</td>
                    <td>{{userInfo.cstdAdmDivsison}}</td>
                    <td>{{userInfo.loginStatus}}</td>
                    <td>{{userInfo.loginTime | formatDate}}</td>
                    <td>{{userInfo.attempt}}</td>
                    <td>{{userInfo.needApprove}}</td>
                    <td>{{userInfo.logoutTime | formatDate}}</td>
                    <td>{{userInfo.nationalId}}</td>
                    <td>{{userInfo.cstdOrganizationGrade}}</td>
                    <td>{{userInfo.cstdEmploymentType}}</td>
                    <td>
                        <a v-if="userInfo.manuScript" v-on:click="openFile(userInfo.manuScriptContentType, userInfo.manuScript)">
                            <img v-bind:src="'data:' + userInfo.manuScriptContentType + ';base64,' + userInfo.manuScript" style="max-height: 30px;" alt="userInfo image"/>
                        </a>
                        <span v-if="userInfo.manuScript">{{userInfo.manuScriptContentType}}, {{byteSize(userInfo.manuScript)}}</span>
                    </td>
                    <td>{{userInfo.ccVersion}}</td>
                    <td>
                        {{userInfo.user ? userInfo.user.login : ''}}
                    </td>
                    <td class="text-right">
                        <div class="btn-group flex-btn-group-container">
                            <router-link :to="{name: 'UserInfoView', params: {userInfoId: userInfo.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'UserInfoEdit', params: {userInfoId: userInfo.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(userInfo)"
                                   variant="danger"
                                   class="btn btn-sm"
                                   v-b-modal.removeEntity>
                                <font-awesome-icon icon="times"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                            </b-button>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <b-modal ref="removeEntity" id="removeEntity" >
            <span slot="modal-title"><span id="etaxApp.userInfo.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-userInfo-heading" v-bind:title="$t('etaxApp.userInfo.delete.question')">Are you sure you want to delete this User Info?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-userInfo" v-text="$t('entity.action.delete')" v-on:click="removeUserInfo()">Delete</button>
            </div>
        </b-modal>
        <div v-if="userInfos && userInfos.length">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./user-info.component.ts">
</script>
