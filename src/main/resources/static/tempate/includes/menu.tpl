<!-- BEGIN SIDEBAR -->
<div class="page-sidebar navbar-collapse collapse">
    <!-- BEGIN SIDEBAR MENU -->
    <ul class="page-sidebar-menu  page-header-fixed " data-auto-scroll="true" data-keep-expanded="false"
        data-slide-speed="200" style="padding-top: 20px">
        <li class="sidebar-toggler-wrapper hide">
            <!-- BEGIN SIDEBAR TOGGLER BUTTON -->
            <div class="sidebar-toggler"></div>
            <!-- END SIDEBAR TOGGLER BUTTON -->
        </li>
        {{each level0List as menu}}
            <li class="heading">
                <h3 class="uppercase">{{menu.menuName}}</h3>
            </li>
            {{each level1Map[menu.menuId] as level1Menu}}
                <li class="level1-menu nav-item" id="menu_{{level1Menu.menuId}}">
                    <a class="level1-menu nav-link {{if level1Menu.leaf}}{{level1menu.menuUrl}}{{else}}nav-toggle{{/if}}"
                       href="{{if level1Menu.leaf}}{{level1Menu.menuUrl}}{{else}}javascript:;{{/if}}">
                        {{if level1menu.menuIcon!=''}}<i class="{{level1menu.menuIcon}}"></i>{{/if}}
                        <span class="title">{{level1Menu.menuName}}</span>
                        <span class="selected"></span>
                        {{if !level1Menu.leaf}}
                        <span class="arrow"></span>
                        {{/if}}
                    </a>
                    {{if !level1Menu.leaf}}
                    <ul class="sub-menu">
                        {{each level2Map[level1Menu.menuId] as level2Menu}}
                        <li class="level2-menu nav-item" id="menu_{{level2Menu.menuId}}">
                            <a class="level2-menu nav-link {{if level1Menu.leaf}}{{level1menu.menuUrl}}{{else}}nav-toggle{{/if}}"
                               href="{{level2menu.menuUrl}}">{{if
                                level2menu.menuIcon!=''}}<i class="{{level2menu.menuIcon}}"></i>{{/if}} {{level2Menu.menuName}}
                                {{if !level2Menu.leaf}}
                                <span class="arrow"></span>
                                {{/if}}
                            </a>
                            {{if !level2Menu.leaf}}
                            <ul class="sub-menu">
                                {{each level3Map[level2Menu.menuId] as level3Menu}}
                                <li class="nav-item" id="menu_{{level3Menu.menuId}}">
                                    <a class="nav-link" href="{{level3menu.menuUrl}}">{{if level3menu.menuIcon!=''}}<i
                                            class="{{level3menu.menuIcon}}"></i>{{/if}} {{level3Menu.menuName}}</a>
                                </li>
                                {{/each}}
                            </ul>
                            {{/if}}
                        </li>
                        {{/each}}
                    </ul>
                    {{/if}}
                </li>
            {{/each}}
        {{/each}}

    </ul>
</div>