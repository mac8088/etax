<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="etaxApp.publicHoliday.home.createOrEditLabel" v-text="$t('etaxApp.publicHoliday.home.createOrEditLabel')">Create or edit a PublicHoliday</h2>
                <div>
                    <div class="form-group" v-if="publicHoliday.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="publicHoliday.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.publicHoliday.cstdHolidayTypes')" for="public-holiday-cstdHolidayTypes">Cstd Holiday Types</label>
                        <input type="text" class="form-control" name="cstdHolidayTypes" id="public-holiday-cstdHolidayTypes"
                            :class="{'valid': !$v.publicHoliday.cstdHolidayTypes.$invalid, 'invalid': $v.publicHoliday.cstdHolidayTypes.$invalid }" v-model="$v.publicHoliday.cstdHolidayTypes.$model" />
                        <div v-if="$v.publicHoliday.cstdHolidayTypes.$anyDirty && $v.publicHoliday.cstdHolidayTypes.$invalid">
                            <small class="form-text text-danger" v-if="!$v.publicHoliday.cstdHolidayTypes.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 40 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.publicHoliday.description')" for="public-holiday-description">Description</label>
                        <input type="text" class="form-control" name="description" id="public-holiday-description"
                            :class="{'valid': !$v.publicHoliday.description.$invalid, 'invalid': $v.publicHoliday.description.$invalid }" v-model="$v.publicHoliday.description.$model" />
                        <div v-if="$v.publicHoliday.description.$anyDirty && $v.publicHoliday.description.$invalid">
                            <small class="form-text text-danger" v-if="!$v.publicHoliday.description.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 70 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.publicHoliday.date')" for="public-holiday-date">Date</label>
                        <div class="input-group">
                            <input id="public-holiday-date" type="date" class="form-control" name="date"  :class="{'valid': !$v.publicHoliday.date.$invalid, 'invalid': $v.publicHoliday.date.$invalid }"
                            v-model="$v.publicHoliday.date.$model"  required />
                        </div>
                        <div v-if="$v.publicHoliday.date.$anyDirty && $v.publicHoliday.date.$invalid">
                            <small class="form-text text-danger" v-if="!$v.publicHoliday.date.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.publicHoliday.workingFlag')" for="public-holiday-workingFlag">Working Flag</label>
                        <input type="checkbox" class="form-check" name="workingFlag" id="public-holiday-workingFlag"
                            :class="{'valid': !$v.publicHoliday.workingFlag.$invalid, 'invalid': $v.publicHoliday.workingFlag.$invalid }" v-model="$v.publicHoliday.workingFlag.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.publicHoliday.countryWide')" for="public-holiday-countryWide">Country Wide</label>
                        <input type="checkbox" class="form-check" name="countryWide" id="public-holiday-countryWide"
                            :class="{'valid': !$v.publicHoliday.countryWide.$invalid, 'invalid': $v.publicHoliday.countryWide.$invalid }" v-model="$v.publicHoliday.countryWide.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.publicHoliday.ccVersion')" for="public-holiday-ccVersion">Cc Version</label>
                        <input type="number" class="form-control" name="ccVersion" id="public-holiday-ccVersion"
                            :class="{'valid': !$v.publicHoliday.ccVersion.$invalid, 'invalid': $v.publicHoliday.ccVersion.$invalid }" v-model.number="$v.publicHoliday.ccVersion.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.publicHoliday.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./public-holiday-update.component.ts">
</script>
