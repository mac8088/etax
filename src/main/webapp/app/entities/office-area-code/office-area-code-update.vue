<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="etaxApp.officeAreaCode.home.createOrEditLabel" v-text="$t('etaxApp.officeAreaCode.home.createOrEditLabel')">Create or edit a OfficeAreaCode</h2>
                <div>
                    <div class="form-group" v-if="officeAreaCode.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="officeAreaCode.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.officeAreaCode.officeId')" for="office-area-code-officeId">Office Id</label>
                        <input type="number" class="form-control" name="officeId" id="office-area-code-officeId"
                            :class="{'valid': !$v.officeAreaCode.officeId.$invalid, 'invalid': $v.officeAreaCode.officeId.$invalid }" v-model.number="$v.officeAreaCode.officeId.$model"  required/>
                        <div v-if="$v.officeAreaCode.officeId.$anyDirty && $v.officeAreaCode.officeId.$invalid">
                            <small class="form-text text-danger" v-if="!$v.officeAreaCode.officeId.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.officeAreaCode.officeId.number" v-text="$t('entity.validation.number')">
                                This field should be a number.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.officeAreaCode.fromPin')" for="office-area-code-fromPin">From Pin</label>
                        <input type="text" class="form-control" name="fromPin" id="office-area-code-fromPin"
                            :class="{'valid': !$v.officeAreaCode.fromPin.$invalid, 'invalid': $v.officeAreaCode.fromPin.$invalid }" v-model="$v.officeAreaCode.fromPin.$model"  required/>
                        <div v-if="$v.officeAreaCode.fromPin.$anyDirty && $v.officeAreaCode.fromPin.$invalid">
                            <small class="form-text text-danger" v-if="!$v.officeAreaCode.fromPin.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.officeAreaCode.fromPin.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 10 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.officeAreaCode.toPin')" for="office-area-code-toPin">To Pin</label>
                        <input type="text" class="form-control" name="toPin" id="office-area-code-toPin"
                            :class="{'valid': !$v.officeAreaCode.toPin.$invalid, 'invalid': $v.officeAreaCode.toPin.$invalid }" v-model="$v.officeAreaCode.toPin.$model" />
                        <div v-if="$v.officeAreaCode.toPin.$anyDirty && $v.officeAreaCode.toPin.$invalid">
                            <small class="form-text text-danger" v-if="!$v.officeAreaCode.toPin.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 10 characters.
                            </small>
                        </div>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.officeAreaCode.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./office-area-code-update.component.ts">
</script>
