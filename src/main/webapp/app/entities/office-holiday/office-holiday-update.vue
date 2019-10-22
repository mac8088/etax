<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="etaxApp.officeHoliday.home.createOrEditLabel" v-text="$t('etaxApp.officeHoliday.home.createOrEditLabel')">Create or edit a OfficeHoliday</h2>
                <div>
                    <div class="form-group" v-if="officeHoliday.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="officeHoliday.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.officeHoliday.officeId')" for="office-holiday-officeId">Office Id</label>
                        <input type="number" class="form-control" name="officeId" id="office-holiday-officeId"
                            :class="{'valid': !$v.officeHoliday.officeId.$invalid, 'invalid': $v.officeHoliday.officeId.$invalid }" v-model.number="$v.officeHoliday.officeId.$model"  required/>
                        <div v-if="$v.officeHoliday.officeId.$anyDirty && $v.officeHoliday.officeId.$invalid">
                            <small class="form-text text-danger" v-if="!$v.officeHoliday.officeId.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.officeHoliday.officeId.number" v-text="$t('entity.validation.number')">
                                This field should be a number.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.officeHoliday.holidayId')" for="office-holiday-holidayId">Holiday Id</label>
                        <input type="text" class="form-control" name="holidayId" id="office-holiday-holidayId"
                            :class="{'valid': !$v.officeHoliday.holidayId.$invalid, 'invalid': $v.officeHoliday.holidayId.$invalid }" v-model="$v.officeHoliday.holidayId.$model"  required/>
                        <div v-if="$v.officeHoliday.holidayId.$anyDirty && $v.officeHoliday.holidayId.$invalid">
                            <small class="form-text text-danger" v-if="!$v.officeHoliday.holidayId.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.officeHoliday.holidayId.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 7 characters.
                            </small>
                        </div>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.officeHoliday.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./office-holiday-update.component.ts">
</script>
